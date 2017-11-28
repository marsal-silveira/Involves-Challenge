package br.com.marsalsilveira.readr.service;

import br.com.marsalsilveira.readr.service.command.CommandProvider;
import br.com.marsalsilveira.readr.service.command.CommandResponse;
import br.com.marsalsilveira.readr.service.command.InvalidInputException;
import br.com.marsalsilveira.readr.service.command.ReadrCommand;
import br.com.marsalsilveira.readr.service.file.FileFactory;
import br.com.marsalsilveira.readr.service.file.InvalidFileException;
import br.com.marsalsilveira.readr.service.file.ReadrFile;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service main class.
 */
public enum Service implements ReadrService {

    // we didn't use SHARED to looks like more `elegant` when call it. e.g: Service.shared.commands().
    shared;

    //******************************************************************************************************************
    //* Properties
    //******************************************************************************************************************

    private ReadrFile _file = null;
    private boolean _initialized = false;

    //******************************************************************************************************************
    //* Constructor
    //******************************************************************************************************************

    Service() {}

    //******************************************************************************************************************
    //* Setup | Assert
    //******************************************************************************************************************

    public void setup(String filePath) throws FileNotFoundException, InvalidFileException {

        _file = FileFactory.createFile(filePath);
        _initialized = true;
    }

    private void assertInitialized() {

        // if service isn't initialized throw a runtime exception to abort execution.
        // the program can't continue in this state.
        if (!_initialized) {

            throw new RuntimeException("Service isn't initialized. Please call `Service.shared.setup(filePath)` before use it.");
        }
    }

    //******************************************************************************************************************
    //* Service
    //******************************************************************************************************************

    public List<String> commands() {

        this.assertInitialized();

        return CommandProvider.commands().stream().map(command -> command.fullDescription()).collect(Collectors.toList());
    }

    public List<String> fields() {

        this.assertInitialized();

        return _file.fields();
    }

    public CommandResponse execCommand(String input) throws InvalidInputException {

        this.assertInitialized();

        ReadrCommand command = CommandProvider.command(input);
        return command.exec(input, _file);
    }
}