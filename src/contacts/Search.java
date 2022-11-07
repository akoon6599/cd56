package contacts;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.regex.*;

public class Search {
    static ArrayList<String> Errors = new ArrayList<>();
    static ArrayList<Contact> Contacts = new ArrayList<>();
    static HashMap<String, Integer> LongestFields = new HashMap<>(3);

    public static void main(String[] args) throws FileNotFoundException {
        LongestFields.put("Names",0);
        LongestFields.put("Ages",0);
        LongestFields.put("Addresses",0);
        File f = new File("./src/contacts/contacts.txt");
        Scanner file_scan = new Scanner(f);

        Pattern ptn = Pattern.compile("(?:.*)^(?<Name>[A-Za-z]+-?[A-Za-z]+ [A-Za-z]+)\\s+(?<Age>[0-9]+)\\s+(?<Address>(?<Number>[0-9]+)(?<Road>(?: [A-Za-z]{2})? [A-Za-z]+ [A-Za-z]{2,4})\\.)$");

        while(file_scan.hasNext()) {
            String activeLine = file_scan.nextLine();
            Matcher matcher = ptn.matcher(activeLine);
            matcher.matches();
            try {
                Contacts.add(new Contact(matcher.group("Name"), Integer.parseInt(matcher.group("Age")), Integer.parseInt(matcher.group("Number")), matcher.group("Road").replaceFirst(" ","")));
            }
            catch (Exception e) {
                Errors.add(String.format("%s, %s", e, activeLine));
            }
        }


        Scanner scanner = new Scanner(System.in);
        String HELP_INFO = """
                >help (): display this message
                >display (): display list of all contacts in LastName,FirstName,Age format
                >create (FirstName,LastName,Age,Address): add a new contact - requires additional enter
                >whois (FirstName,LastName,Age): retrieve address of contact
                >delete (FirstName,Lastname,Age): delete a contact>
                >update (FirstName,LastName,Age,newFirstName,newLastName,newAge,newAddress): change details of an existing contact - requires additional enter
                >export (FileName): exports contact list to local .CSV file
                >exit (): exit program\n\n""";
        System.out.println(HELP_INFO);
        REPL:
        while (true) {
            sortContacts();
            System.out.print(">>>  ");
            String cmd = scanner.next().toLowerCase();


            switch (cmd) {
                case "create":
                    createContact(scanner.next(), scanner.next(), Integer.parseInt(scanner.next()), Integer.parseInt(scanner.next()), scanner.next());
                    break;
                case "display":
                    System.out.println(displayShortContacts());
                    break;
                case "whois": {
                    String FirstName = scanner.next();
                    String LastName = scanner.next();
                    Integer Age = Integer.parseInt(scanner.next());
                    System.out.println(displayLongContact(LastName + ", " + FirstName, Age));
                    break;
                }
                case "delete": {
                    String FirstName = scanner.next();
                    String LastName = scanner.next();
                    Integer Age = Integer.parseInt(scanner.next());
                    deleteContact(LastName + ", " + FirstName, Age);
                    break;
                }
                case "update": {
                    String FirstName = scanner.next();
                    String LastName = scanner.next();
                    Integer Age = Integer.parseInt(scanner.next());
                    editContact(LastName + ", " + FirstName, Age,
                            scanner.next(), scanner.next(), Integer.parseInt(scanner.next()), Integer.parseInt(scanner.next()),
                            scanner.nextLine());
                    break;
                }
                case "export":
                    try {
                        exportContacts(scanner.next());
                    } catch (IOException e) {
                        System.out.print("\nFile Not Created\n");
                    }
                    break;
                case "exit":
                    break REPL;
                case "help":
                    System.out.println(HELP_INFO);
                    break;
                default:
                    System.out.printf("\nUnknown Command %s\n%n",cmd);
                    Errors.add(String.format("Unknown Command Error: %s", cmd));
                    break;
            }

            scanner.nextLine();
        }
        System.out.println("\n<<< Detected Errors: ");
        for (String s : Errors) {
            System.out.println(s);
        }
    }

    public static void sortContacts() {
        for (int i = 0; i < Contacts.size()-1; i++) {
            String[] currentNames = Contacts.get(i).Name.split(", ");
            String[] nextNames = Contacts.get(i+1).Name.split(", ");
            if (currentNames[0].equals(nextNames[0])) {
                int currentLength = currentNames[1].length(), nextLength = nextNames[1].length(),
                        minLength = Math.min(currentLength, nextLength);
                for (int a=0; a<minLength; a++) {
                    int currentLetter = currentNames[1].toLowerCase().charAt(a);
                    int nextLetter = nextNames[1].toLowerCase().charAt(a);
                    if (currentLetter > nextLetter) {
                        Contact tempCn = Contacts.get(i);
                        Contacts.set(i, Contacts.get(i+1));
                        Contacts.set(i+1, tempCn);
                        break;
                    } else if (currentLetter != nextLetter) {break;}
                }
            }
            else {
                int currentLength = currentNames[0].length(), nextLength = nextNames[0].length(),
                        minLength = Math.min(currentLength, nextLength);
                for (int a = 0; a < minLength; a++) {
                    int currentLetter = currentNames[0].toLowerCase().charAt(a);
                    int nextLetter = nextNames[0].toLowerCase().charAt(a);
                    if (currentLetter > nextLetter) {
                        Contact tempCn = Contacts.get(i);
                        Contacts.set(i, Contacts.get(i+1));
                        Contacts.set(i+1, tempCn);
                        break;
                    } else if (currentLetter != nextLetter) {break;}
                }
            }
        }
        for (int i = Contacts.size()-1; i > 0; i--) {
            String[] currentNames = Contacts.get(i).Name.split(", ");
            String[] nextNames = Contacts.get(i-1).Name.split(", ");
            int currentLength = currentNames[0].length(), nextLength = nextNames[0].length(),
                    minLength = Math.min(currentLength, nextLength);
            for (int a = 0; a < minLength; a++) {
                int currentLetter = currentNames[0].toLowerCase().charAt(a);
                int nextLetter = nextNames[0].toLowerCase().charAt(a);
                if (currentLetter < nextLetter) {
                    Contact tempCn = Contacts.get(i);
                    Contacts.set(i, Contacts.get(i-1));
                    Contacts.set(i-1, tempCn);
                    break;
                } else if (currentLetter != nextLetter) {break;}
            }
        }
    }

    public static String displayShortContacts() {
        LongestFields.replace("Names",0);
        StringBuilder st = new StringBuilder();
        for (Contact c : Contacts) {
            if (c.Name.length() > LongestFields.get("Names")) {LongestFields.replace("Names",c.Name.length());}
        }
        for (Contact c: Contacts) {
            st.append(String.format("%s,%s %d%n",
                    c.Name," ".repeat(LongestFields.get("Names")-c.Name.length()), c.Age));
        }
        return st.toString();
    }
    public static String displayLongContact(String Name, Integer Age) {
        Contact d = new Contact("NULL NULL",999,999,"NULL ST.");
        StringBuilder st = new StringBuilder();
        for (Contact c : Contacts) {
            if (c.checkForIdentity(Name, Age)) {d=c;}
        }
        st.append(String.format("%s, %d - %s%n", d.Name,d.Age,d.Address));
        return st.toString();
    }
    public static String displayFullContacts() {
        LongestFields.replace("Names",0);
        LongestFields.replace("Ages",0);
        LongestFields.replace("Addresses",0);
        StringBuilder st = new StringBuilder();
        for (Contact c : Contacts) {
            if (c.Name.length() > LongestFields.get("Names")) {LongestFields.replace("Names",c.Name.length());}
            if (c.Age.toString().length() > LongestFields.get("Ages")) {LongestFields.replace("Ages",c.Age.toString().length());}
            if (c.Address.length() > LongestFields.get("Addresses")) {LongestFields.replace("Addresses",c.Address.length());}
        }
        for (Contact c : Contacts) {
            st.append(String.format("%s,%s %d%s - %s%n",
                    c.Name," ".repeat(LongestFields.get("Names")-c.Name.length()),
                    c.Age,(c.Age.toString().length()==1)?" ":"",
                    c.Address));
        }
        return st.toString();
    }
    public static void createContact(String FirstName, String LastName, Integer Age, Integer AddressNum, String AddressSt) {
        Contacts.add(new Contact(FirstName+" "+LastName,Age,AddressNum, AddressSt));
    }
    public static void deleteContact(String Name, Integer Age) {
        Contact d = new Contact("NULL NULL",999,999,"NULL ST.");
        for (Contact c : Contacts) {if (c.checkForIdentity(Name, Age)) {d=c;}}
        Contacts.remove(d);
    }
    public static void editContact(String OldName, Integer OldAge,
                                   String newFirstName, String newLastName, Integer newAge, Integer newAddressNum, String newAddressStreet) {
        int contactIndex = 0;
        for (Contact c : (ArrayList<Contact>) Search.Contacts.clone()) {
            if (c.checkForIdentity(OldName, OldAge)) {
                Search.Contacts.set(contactIndex, new Contact(newFirstName+" "+newLastName, newAge, newAddressNum, newAddressStreet));
            }
            contactIndex++;
        }
    }

    public static void exportContacts(String fileName) throws IOException {
        File newFile = new File(String.format("./%s.csv",fileName));
        newFile.createNewFile();
        FileWriter writer = new FileWriter(newFile.getPath());

        StringBuilder st = new StringBuilder();
        for (Contact c : Contacts) {
            st.append(String.format("%s,%d,%s%n", c.Name,c.Age, c.Address));
        }
        writer.write(st.toString());
        writer.close();
    }
}
