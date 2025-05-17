# CLI Minesweeper in Java
This project is a command-line version of the classic Minesweeper game, built in Java with a focus on clean architecture.

>[!NOTE]
> Game developed with JDK 21, but minimun required Java version is Java 17.

### On Linux Terminal
```bash
    # clones repository to current diractory
    git clone https://github.com/sioterino/minesweeper.git
    
    # changes directory to the projects directory
    cd minesweeper/
    
    # creates runnable distribution of the app
    ./gradlew installDist
    
    # runs the cli program
    ./app/build/install/app/bin/app
```

> [!WARNING]
> In case access permission is denied by the System while trying to run `./gradlew installDist`, you can allow access with the following command:
> ```bash
>     # allows acess to the gradlew file
>     chmod u+x gradlew
> ```

### On Windows Terminal
```bash
    # clones repository to current diractory
    git clone https://github.com/sioterino/minesweeper.git
    
    # changes directory to the projects directory
    cd minesweeper/
    
    # creates runnable distribution of the app
    gradlew.bat installDist
    
    # runs the cli program
    ./app/build/install/app/bin/app.bat
```

>[!IMPORTANT]
> Heavy inspiration from [this project](https://github.com/jesse-rr/CLI_Blackjack) and [this project](https://github.com/Squirrelbear/Minesweeper/)

## Contributing
I welcome contributions! Feel free to submit issues or pull requests.

## License
This project is licensed under the [MIT License](LICENSE).
