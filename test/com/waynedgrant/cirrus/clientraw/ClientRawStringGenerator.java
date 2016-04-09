package com.waynedgrant.cirrus.clientraw;

public class ClientRawStringGenerator
{
    private static final char FIELD_DELIMITER = ' ';
    
    public String create(Field... fields)
    {
        String[] fieldArray = createFieldArray(fields);
        
        return convertFieldArrayToClientRawText(fieldArray);
    }

    private String[] createFieldArray(Field... fields)
    {
        String[] fieldArray = {};
        
        int highestPosition = -1;
        
        for (Field field : fields)
        {
            if (field.getPosition() > highestPosition)
            {
                highestPosition = field.getPosition();
            }
        }        
        
        if (highestPosition >= 0)
        {
            fieldArray = new String[highestPosition + 1];
            
            for (int i=0; i < fieldArray.length; i++)
            {
                fieldArray[i] = "-";
            }
            
            for (Field field : fields)
            {
                fieldArray[field.getPosition()] = field.getValue();
            }
        }
        
        return fieldArray;
    }
    
    private String convertFieldArrayToClientRawText(String[] fieldArray)
    {
        StringBuffer strBuff = new StringBuffer();
        
        for (int i=0; i < fieldArray.length; i++)
        {
            strBuff.append(fieldArray[i]);
            
            if ((i+1) < fieldArray.length)
            {
                strBuff.append(FIELD_DELIMITER);
            }
        }
        
        if (fieldArray.length > 0)            
        {
            strBuff.append("\n");
        }
        
        return strBuff.toString();
    }
    
    public static class Field
    {
        private int position;
        private String value;
        
        public Field(int position, String value)
        {
            this.position = position;
            this.value = value;
        }
        
        public int getPosition()
        {
            return position;
        }
        
        public String getValue()
        {
            return value;
        }
    }
}
