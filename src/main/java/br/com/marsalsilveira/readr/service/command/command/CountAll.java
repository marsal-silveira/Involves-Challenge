package br.com.marsalsilveira.readr.service.command.command;

import br.com.marsalsilveira.readr.exception.InvalidInputException;
import br.com.marsalsilveira.readr.service.command.CommandResponse;
import br.com.marsalsilveira.readr.service.command.ReadrCommand;
import br.com.marsalsilveira.readr.service.file.model.ReadrFile;
import br.com.marsalsilveira.readr.utils.CollectionUtils;
import br.com.marsalsilveira.readr.utils.StringUtils;

import java.util.List;

/**
 *
 */
public class CountAll implements ReadrCommand {

    //******************************************************************************************************************
    //* Strings
    //******************************************************************************************************************

    // we put these strings here instead `Strings` because Strings should be independent from commands...
    // so these strings will break this principle.
    private static String command = "count *";
    private static String description = "Return the number of records in file without applying any criteria.";
    public static String fullDescription = command + " -> " + description;
    public static String response = "File has `%d` record(s).";

    //******************************************************************************************************************
    //* Properties
    //******************************************************************************************************************

    public String command() { return CountAll.command; }
    public String description() { return CountAll.description; }
    public String fullDescription() { return CountAll.fullDescription; }

    //******************************************************************************************************************
    //* Constructor
    //******************************************************************************************************************

    public CountAll() { }

    //******************************************************************************************************************
    //* Execution
    //******************************************************************************************************************

    public CommandResponse exec(String input, ReadrFile file) throws InvalidInputException {

        if (!CountAll.Validator.isValid(input)) {

            throw new InvalidInputException();
        }

        String result = String.format(CountAll.response, file.count());

        CommandResponse response = new CommandResponse();
        response.addMessage(result);
        return response;
    }

    //******************************************************************************************************************
    //* Validator
    //******************************************************************************************************************

    public static final class Validator {

        // Avoid to create it
        private Validator() { }

        //******************************************************************************************************************
        //* Validation
        //******************************************************************************************************************

        public static boolean isValid(String input) {

            if (StringUtils.isEmpty(input)) {

                return false;
            }

            List<String> parts = CollectionUtils.toList(input.toLowerCase());
            return (parts.size() == 2)
                && (parts.get(0).equals("count"))
                && (parts.get(1).equals("*"));
        }
    }
}