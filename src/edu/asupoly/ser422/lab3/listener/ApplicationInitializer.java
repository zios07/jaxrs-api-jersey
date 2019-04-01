package edu.asupoly.ser422.lab3.listener;

import static edu.asupoly.ser422.lab3.utils.Utils.CONFIG_FILE;
import static edu.asupoly.ser422.lab3.utils.Utils.CONFIG_PHONEBOOK_FILES_PATH_KEY;
import static edu.asupoly.ser422.lab3.utils.Utils.CONFIG_PHONEBOOK_INIT_COUNT_KEY;
import static edu.asupoly.ser422.lab3.utils.Utils.EXTENSION;
import static edu.asupoly.ser422.lab3.utils.Utils.FILENAME;
import static edu.asupoly.ser422.lab3.utils.Utils.LNAMES;
import static edu.asupoly.ser422.lab3.utils.Utils.NAMES;
import static edu.asupoly.ser422.lab3.utils.Utils.PHONES;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import edu.asupoly.ser422.lab3.model.PhoneEntry;

@WebListener
public class ApplicationInitializer implements ServletContextListener {

	private Gson gson = new GsonBuilder().setPrettyPrinting().create();

	@Override
	public void contextInitialized(ServletContextEvent event) {
		Properties properties = new Properties();
		try {
			properties.load(ApplicationInitializer.class.getClassLoader().getResourceAsStream(CONFIG_FILE));
			if (properties.get(CONFIG_PHONEBOOK_INIT_COUNT_KEY) != null) {
				String fullpath;
				PhoneEntry[] result;
				int j = 1;
				result = new PhoneEntry[9];
				for (int i = 0; i < 9; i++) {
					result[i] = new PhoneEntry(NAMES[i], LNAMES[i], PHONES[i]);
				}
				fullpath = (String) properties.get(CONFIG_PHONEBOOK_FILES_PATH_KEY) + "/" + FILENAME + j + EXTENSION;
				String json = gson.toJson(result);
				System.out.println(json);
				FileWriter writer = new FileWriter(fullpath);
				writer.write(json);
				writer.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {

	}
}