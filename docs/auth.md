# Register and Authentication UML Class Diagram

## 1. Models Diagram

**Purpose**: Represents the core domain entities (data models) used in user registration and authentication.

* **User**: Contains login info, performance stats (wins/losses), and guest status.
* **Player**: Wraps a User and adds functionality for stats and guest creation.
* **Stats**: Encapsulates win/loss data and computes derived stats like games played and win rate.

### Relationships:
- `Player` _aggregates_ a `User`.
- `Player` _composes_ `Stats`, meaning each player has a unique set of stats.

```mermaid
---
title: Models
---
classDiagram
    class User {
        - String login
        - String password
        - int wins
        - int losses
        - boolean isGuest
    }

    class Player {
        - User user
        + Player(User user)
        + getStats() Stats
        + increaseWins() void
        + increaseLosses() void
        + guestPlayer() Player$
    }

    class Stats {
        - int wins
        - int losses
        - int gamesPlayed
        - String winRate
        + Stats(int wins, int losses)
    }

    Player o-- User : aggregates
    Player *-- Stats : composes
    
```

## 2. Service & Repository Diagram

**Purpose**: Defines the service and data access layers responsible for user management and security.

* **UserRepository (interface)**: Declares methods for user storage and retrieval.
* **FileUserRepository**: Implements `UserRepository` using a file-based system.
* **HashAlgorithm (interface)**: Abstracts password hashing and comparison.
* **BCryptHashAlgorithm**: Concrete implementation using the BCrypt algorithm.
* **UserService**: Contains business logic for registering, authenticating, and editing users.
* **UserException**: Base exception for user-related errors.

### Relationships:
* `UserService` _depends_ on both `UserRepository` and `HashAlgorithm`.
* _Implements_ relationships connect interfaces to their concrete classes.

```mermaid
---
title: Service & Repository
---
classDiagram
    class UserRepository {
        <<interface>>
        + save(User) void
        + editPassword(String, User) void
        + editLogin(String, User) void
        + findByLogin(String) User
    }

    class FileUserRepository {
        - File file
        - Map users
        + save(User) void
        + editPassword(String, User) void
        + editLogin(String, User) void
        + findByLogin(String) User
    }

    class HashAlgorithm {
        <<interface>>
        + hash(String) String
        + compare(String, String) boolean
    }

    class BCryptHashAlgorithm {
        + hash(String) String
        + compare(String, String) boolean
    }

    class UserService {
        - UserRepository repository
        - HashAlgorithm hashAlgorithm
        + register(String, String) void
        + authenticate(String, String) Player
        + editPassword(String, String, String) void
        + editLoginName(String, String, String) void
    }
    
    class UserException

    UserRepository <|.. FileUserRepository : implements
    HashAlgorithm <|.. BCryptHashAlgorithm : implements
    UserService ..> UserRepository : depends
    UserService ..> HashAlgorithm : depends
    
    UserService .. UserException : throws

```

## 3. Controller / UI Utilities Diagram

**Purpose**: Manages user interaction and coordinates input/output flow.

* **UserController**: Main control class handling UI logic and flow (register, login, guest play).
* **ASCIIMenu**: Enum representing different UI states/screens.
* **Terminal**: Utility to clear the console screen.
* **InputHandler**: Handles validated user input (with exception throwing).
* **InvalidInputException**: Exception thrown for improper input.

### Relationships:
* `UserController` uses `UserService`, `ASCIIMenu`, `Terminal`, and `InputHandler`.

```mermaid
---
title: Controller / UI Utilities
---
classDiagram
    class UserController {
        - Scanner scanner
        - UserService service
        + start() void
        - redirect(ASCIIMenu) void
        - handleUserInput() void
        - userRegister() void
        - userAuthenticate() void
        - playAsGuest() void
        - safeExit() void
    }

    class ASCIIMenu {
        <<enumeration>>
        + LOGIN
        + MAIN
        + CREATE_NEW_ACCOUNT
        + AUTHENTICATE_ACCOUNT
    }

    class Terminal {
        + clearConsole() void
    }

    class InputHandler {
        + defaultYes(String) boolean
        + username(String) String
        + password(String) String
    }
    
    class InvalidInputException

    UserController ..> UserService : dependes
    UserController --> ASCIIMenu : uses
    UserController --> Terminal : uses
    UserController --> InputHandler : uses
    
    InputHandler .. InvalidInputException : throws

```

## 4. Exception Hierarchy

**Purpose**: Organizes all custom exceptions used in user management.

* **UserException**: Base class for all user-related exceptions.
  * Includes subclasses for invalid login, password, user existence, and not found errors.
* **InvalidInputException**: Separate branch for handling bad user input during UI interaction.

**Hierarchy**: Clearly structures exceptions for fine-grained error handling across layers.

```mermaid
---
title: Exception Hierarchy
---
    classDiagram
        class UserException
        class InvalidLoginException
        class InvalidPasswordException
        class UserAlreadyExistsException
        class UserNotFoundException
        
        class InvalidInputException
    
        UserException <|-- InvalidLoginException
        UserException <|-- InvalidPasswordException
        UserException <|-- UserAlreadyExistsException
        UserException <|-- UserNotFoundException

```

## Diagrams Relationships
**Purpose**: This final diagram brings together all components of the system—models, services, repositories, UI controllers, and exceptions—into a unified view. It illustrates how different layers interact with each other to support user registration, authentication, and gameplay entry (including guest mode).

* At the core of the system, the User, Player, and Stats models represent the domain data. These are managed by the UserService, which handles business logic and delegates data persistence to the UserRepository (implemented by FileUserRepository) and security tasks to the HashAlgorithm (via BCryptHashAlgorithm).
  <br><br>
* The UI layer, centered around the UserController, coordinates user interaction using utility classes like InputHandler, ASCIIMenu, and Terminal. It interacts directly with the UserService to process input, execute user actions, and handle flow control.
  <br><br>
* Custom exceptions like InvalidLoginException and UserAlreadyExistsException propagate through these layers to signal validation and logic failures, supporting a clear and maintainable error-handling mechanism.

This integrated diagram shows the complete architecture and data flow, reflecting how each part contributes to a robust and modular user management system.

```mermaid
classDiagram
    
    %% entry
    class App {
        + Player player
        + main(String[] args)
    }
      
    namespace Models {
        class Player {
            - User user
            + Player(User user)
            + getStats() Stats
            + increaseWins() void
            + increaseLosses() void
            + guestPlayer() Player$
        }

        class Stats {
            - int wins
            - int losses
            - int gamesPlayed
            - String winRate
            + Stats(int wins, int losses)
        }
        
        class User {
            - String login
            - String password
            - int wins
            - int losses
            - boolean isGuest
        }
    }
    
    
    namespace Service {
        class UserRepository {
            <<interface>>
            + save(User) void
            + editPassword(String, User) void
            + editLogin(String, User) void
            + findByLogin(String) User
        }

        class FileUserRepository {
            - File file
            - Map users
            + save(User) void
            + editPassword(String, User) void
            + editLogin(String, User) void
            + findByLogin(String) User
        }

        class HashAlgorithm {
            <<interface>>
            + hash(String) String
            + compare(String, String) boolean
        }

        class BCryptHashAlgorithm {
            + hash(String) String
            + compare(String, String) boolean
        }

        class UserService {
            - UserRepository repository
            - HashAlgorithm hashAlgorithm
            + register(String, String) void
            + authenticate(String, String) Player
            + editPassword(String, String, String) void
            + editLoginName(String, String, String) void
        }
    }
    
    namespace Controller {
        class UserController {
            - Scanner scanner
            - UserService service
            + start() void
            - redirect(ASCIIMenu) void
            - handleUserInput() void
            - userRegister() void
            - userAuthenticate() void
            - playAsGuest() void
            - safeExit() void
        }

        class ASCIIMenu {
            <<enumeration>>
            + LOGIN
            + MAIN
            + CREATE_NEW_ACCOUNT
            + AUTHENTICATE_ACCOUNT
        }

        class Terminal {
            + clearConsole() void
        }

        class InputHandler {
            + defaultYes(String) boolean
            + username(String) String
            + password(String) String
        }
    }

    namespace Exceptions {
        class UserException
        class InvalidLoginException
        class InvalidPasswordException
        class UserAlreadyExistsException
        class UserNotFoundException

        class InvalidInputException
    }
    
    %% relationships
    
    HashAlgorithm <|.. BCryptHashAlgorithm : implements
    UserRepository <|.. FileUserRepository : implements
    
    UserException <|-- InvalidLoginException : extends
    UserException <|-- InvalidPasswordException : extends
    UserException <|-- UserAlreadyExistsException : extends
    UserException <|-- UserNotFoundException : extends
    
    Player o-- User
    Player *-- Stats

    UserRepository --> User : handles

    UserService ..> UserRepository : depends
    UserService ..> HashAlgorithm : depends

    UserController ..> UserService : depends
    
    UserController --> ASCIIMenu : shows
    
    UserController --> InputHandler : uses
    UserController --> Terminal : uses
    
    App --> UserController : uses

    UserController .. InvalidInputException : throws
    UserService .. UserException : throws

```
