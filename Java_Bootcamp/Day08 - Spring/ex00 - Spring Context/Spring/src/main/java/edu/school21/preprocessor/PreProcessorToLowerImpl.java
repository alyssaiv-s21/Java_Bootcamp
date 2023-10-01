package edu.school21.preprocessor;

public class PreProcessorToLowerImpl implements PreProcessor{
    @Override
    public String preprocessing(String message) {
        return message.toLowerCase();
    }
}
