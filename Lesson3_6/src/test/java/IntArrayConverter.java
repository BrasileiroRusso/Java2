import org.junit.jupiter.params.converter.ArgumentConversionException;
import org.junit.jupiter.params.converter.SimpleArgumentConverter;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class IntArrayConverter extends SimpleArgumentConverter {
    private static final Pattern pattern = Pattern.compile("\\s*\\{(.*)}\\s*");
    private static final String sepRegex = "\\s+";

    @Override
    protected Object convert(Object source, Class<?> targetType) throws ArgumentConversionException {
        if (source instanceof String && int[].class.isAssignableFrom(targetType)) {
            Matcher match = pattern.matcher((String) source);
            if(match.find()){
                String[] strArray = match.group(1).trim().split(sepRegex);
                if(strArray.length == 1 && strArray[0].equals(""))
                    return new int[0];
                int[] result = new int[strArray.length];
                try{
                    for(int i = 0;i < strArray.length;i++){
                        result[i] = Integer.parseInt(strArray[i]);
                    }
                } catch(NumberFormatException e){
                    throw new IllegalArgumentException("Некорректная строка целых чисел");
                }
                return result;
            }
            else
                throw new IllegalArgumentException("Некорректный формат для содержимого массива");
        } else {
            throw new IllegalArgumentException("Конвертация из " + source.getClass() + " в "
                    + targetType + " не поддерживается.");
        }
    }

}
