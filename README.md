# gal-game-maker
A Programming Language for Gal Games

#### Commands:
```
//Configure a game variable. (There is only one variable in the game, but there should be more in the future)
[config] [variableName] value 

//Load resource to the game. The resource could be an image, a folder of images or some background music
[resource] [resourceType] [path = "PATH_HERE"] resourceName

//Variable Declaration. It could be a string, a number or a person in the game
[variable] [variableType]  [variable_data] variableName = value

//Start and end the Game
[startGame]
[endGame]

//Change the title and size of the window
[title] YOUR_TITLE
[size] WIDTH HEIGHT

```

#### Variables:
Strings: like strings in Java, can be used to store character's name or something else.

Numbers: double in Java, can store floating point values.

People: characters in the game. They all have names.
