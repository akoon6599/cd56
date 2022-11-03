package contacts;

public class Contact {
    public String Name;
    protected String FirstName;
    protected String LastName;
    public Integer Age;
    public Integer AddressNum;
    public String AddressStreet;
    public String Address;

    public Contact(String Name, Integer Age, Integer addressNum, String addressStreet) {
        String[] spn = Name.split(" ");
        FirstName = spn[0];
        LastName = spn[1];
        this.Name = String.format("%s, %s",spn[1],spn[0]);
        this.Age = Age;
        this.AddressNum = addressNum;
        this.AddressStreet = addressStreet;
        this.Address = addressNum +" "+addressStreet;
    }
    public boolean checkForIdentity(String[] Name, String Address) {
        return (FirstName.equals(Name[0]) && LastName.equals(Name[1])) && this.Address.equals(Address);
    }
    public boolean checkForIdentity(String Name, String Address) {
        return (this.Name.equals(Name) && this.Address.equals(Address));
    }
    public boolean checkForIdentity(String[] Name, Integer Age) {
        return (FirstName.equals(Name[0]) && LastName.equals(Name[1])) && this.Age.equals(Age);
    }
    public boolean checkForIdentity(String Name, Integer Age) {
        return (this.Name.equals(Name) && this.Age.equals(Age));
    }
    public boolean checkForIdentity(String Name) {
        return (this.Name.equals(Name));
    }

    public String returnFormattedContact() {
        return Name + ", " + Age + " - " + Address;
    }
}
