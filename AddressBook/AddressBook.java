import java.util.ArrayList;
import java.util.Iterator;
/**
 * Manage an AddressBook of Person contacts
 * 
 * @author Joshua Ng, 20163079 
 * @version 10/4/2017
 */
public class AddressBook {

    private ArrayList<Person> contacts;

    /**
     * Create an AddressBook.
     */
    public AddressBook() {
        contacts = new ArrayList<Person>();
    }

    /** Add the person p to the "contacts" list, unless they have the same 
     * surname and first name as a person already in the list, in which case
     * do not add them, but instead print the error message "could not add person".
     * 
     * @param p The person to be added.
     */
    public void addPerson(Person p) {
        if(p != null){
            String pFirName = p.getFirstName(); //initiate variables to save fetching in loop
            String pSurname = p.getSurname();
            Iterator<Person> it = contacts.iterator(); 
            while(it.hasNext()){
                Person i = it.next();
                if(i.getSurname() == pSurname){ //checking surname before firstname to reduce calls
                    if(i.getFirstName() == pFirName){
                        System.out.println("could not add person");
                        return;
                    }
                }
            }
            contacts.add(p);
        }
    }

    /** Search for a person in the "contacts" list by first name and surname,
     * and return the relevant Person object if one matches, or otherwise return null.
     * 
     * @param firstName The first name of the person.
     * @param surname The surname of the person
     * @return The object of class Person relevant to the search.
     */
    public Person findPerson(String firstName, String surname) {
        Iterator<Person> it = contacts.iterator();
        while(it.hasNext()){
            Person i = it.next();
            if(i.getSurname() == surname){
                if(i.getFirstName() == firstName){
                    return i;
                }
            }
        }
        return null;
    }

    /**
     * Find the most social person in the address book.  
     * 
     * @return An object of class Person with the highest Social Activity Level. 
     *          If two or more people have the same highest social media activity level, 
     *          findMostSocial will return the first it finds. findMostSocial searches
     *          contacts sequentially starting from the first added contact.
     */
    public Person findMostSocial() {
        if(contacts.size() != 0){
            Iterator<Person> it = contacts.iterator(); 
            Person i = it.next(); //skip first person.
            Person mostSocial = i; 
            int highLevel = i.getTotalActivityLevel(); //getTotalActivityLevel is a new method in Person. 

            while(it.hasNext()){ //check through contacts for a higher social person.
                i = it.next();
                int iLevel = i.getTotalActivityLevel();
                if(iLevel > highLevel){
                    highLevel = iLevel; 
                    mostSocial = i;
                }
            }
            return mostSocial;
        }
        return null;
    }
}
