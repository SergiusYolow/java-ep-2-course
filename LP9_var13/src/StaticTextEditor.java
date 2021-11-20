import java.beans.PropertyEditorSupport;
import java.util.Locale;

/**
 * This PropertyEditor defines the enumerated values of the alignment property
 * so that a bean box or IDE can present those values to the user for selection
 **/
public class StaticTextEditor extends PropertyEditorSupport
{
    /**
     * Return the list of value names for the enumerated type.
     */
    public String[] getTags()
    {
        return new String[]{"STATIC TEXT", "Sergey", "Rudman", "Sergey Rudman Prod."};
    }

    /**
     * Convert each of those value names into the actual value.
     */
    public void setAsText(String s)
    {
        if (s.toLowerCase(Locale.ROOT).equals("STATIC TEXT".toLowerCase(Locale.ROOT))) setValue("STATIC TEXT");
        else if (s.toLowerCase(Locale.ROOT).equals("Sergey".toLowerCase(Locale.ROOT))) setValue("Sergey");
        else if (s.toLowerCase(Locale.ROOT).equals("Rudman".toLowerCase(Locale.ROOT))) setValue("Rudman");
        else if (s.toLowerCase(Locale.ROOT).equals("Sergey Rudman Prod.".toLowerCase(Locale.ROOT))) setValue("Sergey Rudman Prod.");
        else throw new IllegalArgumentException(s);
    }

    /**
     * This is an important method for code generation.
     */
    public String getJavaInitializationString()
    {
        Object o = getValue();
        if (o == "STATIC TEXT")
            return "TextListButton.StaticText1";
        if (o == "Sergey")
            return "TextListButton.StaticText2";
        if (o == "Rudman")
            return "TextListButton.StaticText3";
        if (o == "Sergey Rudman Prod.")
            return "TextListButton.StaticText4";
        return null;
    }
}
