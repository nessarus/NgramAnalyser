import java.util.ArrayList;
import java.util.Iterator;

/**
 * Manage an AddressBook of Person contacts
 * 
 * @author Joshua Ng, 20163079
 * @version 17/12/2022
 */
public class AddressBook 
{
    private ArrayList<Person> contacts;

    /**
     * Create an AddressBook.
     */
    public AddressBook() 
    {
        contacts = new ArrayList<>();
    }

    /** 
     * Adds a person p to the "contacts" list, unless they have the same 
     * surname and first name as a person already in the list.
     * 
     * @param person    The person to be added.
     */
    public void addPerson(Person person) 
    {
        if (person != null)
        {
            var firstName = person.getFirstName();
            var surname = person.getSurname();
            
            if (findPerson(firstName, surname) == null)
            {
                contacts.add(person);
            }
        }
    }

    /** 
     * Search for a person in the "contacts" list by first name and surname,
     * and return the relevant Person object if one matches, or otherwise 
     * return null.
     * 
     * @param firstName The first name of the person.
     * @param surname   The surname of the person
     * @return The object of class Person relevant to the search.
     */
    public Person findPerson(String firstName, String surname) 
    {
        for (var contact : contacts)
        {
            if
            (
                (contact.getFirstName() == firstName) &&
                (contact.getSurname() == surname)
            )
            {
                return contact;
            }
        }
        
        return null;
    }

    /**
     * Find the most social person in the address book. If two or more 
     * person have the same activity level, the first found will be
     * returned. 
     * 
     * @return The person with the highest social activity level.
     */
    public Person findMostSocial() 
    {        
        if (contacts.isEmpty())
        {
            return null;
        }

        var iterator = contacts.iterator();
        var mostSocial = iterator.next();
        var highestActivity = mostSocial.getTotalActivityLevel();

        while (iterator.hasNext())
        {
            var contact = iterator.next();
            var contactActivity = contact.getTotalActivityLevel();
            
            if (contactActivity > highestActivity)
            {
                highestActivity = contactActivity;
                mostSocial = contact;
            }
        }
        
        return mostSocial;
    }
}
