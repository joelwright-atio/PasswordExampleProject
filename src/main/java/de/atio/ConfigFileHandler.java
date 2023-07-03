package de.atio;

import org.json.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.InputStream;
import java.io.InputStreamReader;


/**

 @file ConfigFileHandler.java
 @brief This class is responsible for reading and parsing the configuration file "config.json".
 It provides methods to retrieve string and boolean values from the JSON object.
 @date 2023-04-20
 */
public class ConfigFileHandler {
    @SuppressWarnings("unused")
    private JSONObject content = new JSONObject();
    /**
     * @brief Retrieves a file from the resources folder as an input stream.
     * @param fileName Name of the file to retrieve.
     * @return InputStream of the retrieved file.
     * @throws IllegalArgumentException Throws an exception if the file is not found.
     */
    private InputStream getFileFromResourceAsStream(String fileName) {
        ClassLoader classLoader = getClass().getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream(fileName);

        if (inputStream == null) {
            throw new IllegalArgumentException("file not found: " + fileName);
        } else {
            return inputStream;
        }
    }

    /**
     * @brief Constructor that initializes the ConfigFileHandler with the contents of the configuration file "config.json".
     * @param filePath Path of the configuration file "config.json".
     * @throws Exception Throws an exception if there is an error while parsing the configuration file.
     */
    public ConfigFileHandler(String filePath) throws Exception {
        InputStream inputStream = getFileFromResourceAsStream(filePath);
        JSONParser parser = new JSONParser();
        // Parse the JSON object from the input stream and store it in content
        Object obj = parser.parse(new InputStreamReader(inputStream, "UTF-8"));
        this.content = new JSONObject(obj.toString());
    }

    /**
     * @brief Retrieves a string value from the JSON object based on the provided path.
     * @param path Path of the string value in the JSON object. Separated by commas.
     * @return The string value at the specified path.
     * @throws Exception Throws an exception if there is an error while retrieving the string value.
     */
    public String GetStringValue(String path) throws Exception {
        JSONObject json = new JSONObject(this.content, JSONObject.getNames(this.content));
        String[] parts = path.split(",");
        for (int x = 0; x < parts.length; x++) {
            Object obj = json.get(parts[x]);
            if (obj instanceof JSONObject) {
                json = json.getJSONObject(parts[x]);
            } else {
                return json.getString(parts[x]);
            }
        }
        throw new Error("No string value found. Please verify path.");
    }

    /**
     * @brief Retrieves a boolean value from the JSON object based on the provided path.
     * @param path Path of the boolean value in the JSON object. Separated by commas.
     * @return The boolean value at the specified path.
     * @throws Exception Throws an exception if there is an error while retrieving the boolean value.
     */
    public Boolean GetBooleanValue(String path) throws Exception {
        JSONObject json = new JSONObject(this.content, JSONObject.getNames(this.content));
        String[] parts = path.split(",");
        for (int x = 0; x < parts.length; x++) {
            Object obj = json.get(parts[x]);
            if (obj instanceof JSONObject) {
                json = json.getJSONObject(parts[x]);
            } else {
                return json.getBoolean(parts[x]);
            }
        }
        throw new Error("No boolean value found. Please verify path.");
    }
}
