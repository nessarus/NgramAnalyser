import java.util.ArrayList;
import java.util.Iterator;

/**
 * Person details including contacts and social media accounts
 * 
 * @author Joshua Ng, 20163079
 * @version 10/4/2017
 */
public class Person 
{
    private ArrayList<SocialMediaAccount> socialMediaAccounts;
    private String firstName;
    private String surname;
    private String mobile;
    private String email;

    /**
     * Create a Person with the first name and surname given by
     * the parameters.
     * 
     * @param firstName A string of the person's first name.
     * @param surname   A string of the person's surname.
     */
    public Person(String firstName, String surname) 
    {
        socialMediaAccounts = new ArrayList<>();
        this.firstName = firstName;
        this.surname = surname;
        mobile = null;
        email = null;
    }

    /**
     * Get the person's first name.
     * 
     * @return the person's first name.
     */
    public String getFirstName() 
    {
        return firstName; 
    }

    /**
     * Set the person's first name unless the parameter is an empty 
     * string.
     * 
     * @param firstName A string of the person's new first name.
     */
    public void setFirstName(String firstName) 
    {
        if ((firstName != null) && !firstName.isEmpty())
        {
            this.firstName = firstName;
        }
    }

    /**
     * Return's the person's surname.
     * 
     * @return A string of the Person's surname.
     */
    public String getSurname() 
    {
        return surname;
    }

    /**
     * Set the person's surname unless the parameter is an empty string.
     * 
     * @param surname A string of the person's new surname.
     */
    public void setSurname(String surname) 
    {
        if ((surname != null) && !surname.isEmpty())
        {
            this.surname = surname;
        }
    }

    /**
     * Return the person's mobile phone number
     * 
     * @return A string of the person's mobile number.
     */
    public String getMobile() 
    {
        return mobile;
    }

    /**
     * Set the person's mobile phone number unless the parameter is an 
     * invalid string. A string is a valid mobile phone number if every 
     * character in it is a digit from 0 to 9.
     * 
     * @param mobile A string of the person's new mobile number.
     */
    public void setMobile(String mobile) 
    {
        if ((mobile != null) && !mobile.isEmpty())
        {
            for (var c: mobile.toCharArray())
            {
                if (!Character.isDigit(c))
                { 
                    return;
                }
            }
            this.mobile = mobile;
        }
    }

    /**
     * Return the person's email address.
     * 
     * @return A string of the person's email address.
     */
    public String getEmail() 
    {
        return email; 
    }

    /**
     * Set the person's email address unless the parameter is an empty 
     * string.
     * 
     * @param email A string of the person's new email address.
     */
    public void setEmail(String email) 
    {
        if((email != null) && !email.isEmpty())
        {
            this.email = email;
        }

    }

    /**
     * Create a new SocialMediaAccount object, and add it to the 
     * socialMediaAccounts list.
     * 
     * @param userID        A string of the user's ID for the website.
     * @param websiteName   A string of the website name.
     * @param websiteURL    A string of the website's URL.
     * @param activityLevel An integer of the social activity level of 
     *                       the user on the website.
     */
    public void addSocialMediaAccount
    (
        String userID, 
        String websiteName, 
        String websiteURL, 
        int activityLevel
    )
    {
        socialMediaAccounts.add(new SocialMediaAccount
        (
            userID, 
            websiteName, 
            websiteURL, 
            activityLevel
        ));
    }

    /**
     * Search the socialMediaAccounts list for an account on the website 
     * specified by the websiteName parameter, and return the userID for 
     * that account. If no such account can be found, return null.
     * 
     * @param   websiteName A string of the webite's name.
     * @return              A string of the user's ID for the website.
     */
    public String getSocialMediaID(String websiteName) 
    {
        for (var account : socialMediaAccounts)
        {
            if (websiteName.equals(account.getWebsiteName()))
            {
                return account.getUserID();
            }
        }
        
        return null;
    }

    /**
     * Return the person's total activity level.
     * 
     * @return An integer for the total social activity level of all the
     *          person's social media accounts.
     */
    public int getTotalActivityLevel()
    {
        int total = 0;
        
        for (var account : socialMediaAccounts)
        {
            total = total + account.getActivityLevel();
        }
        
        return total;
    }

    /** 
     * Print the person's contact details in the format given in the 
     * project specifications.
     */
    public void printContactDetails() 
    {
        var details = new ArrayList<String>();
        details.add(String.format("name: %s %s", firstName, surname));
        
        if (mobile != null)
        {
            details.add(String.format("mobile: %s", mobile));        
        }
        
        if (email != null)
        {
            details.add(String.format("email: %s", email));        
        }
        
        for (var account : socialMediaAccounts)
        {
            details.add(String.format
            (
                "%s, %s, %s", 
                account.getWebsiteName(), 
                account.getUserID(),
                account.getWebsiteURL()
            ));
        }
        
        System.out.println(String.join("\n", details));
    }

}
