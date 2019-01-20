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

//Conversations
[conversation] [owner] the sentence...
E.g.
[conversation] [girlfriend] Hello [[player] name]. My name is Yukino. 
//the [[player] name] part is to get the name of the player (See Accessing Variables below)

//Next image of the images resource (like a stack)
[nextImage] images_resource

//Draw image
[image] image_resource
[image] [index] images_resource //get the image from images resource with the index.
```

#### Variables:
1. Strings: like strings in Java, can be used to store character's name or something else.

2. Numbers: double in Java, can store floating point values.

3. People: characters in the game. They all have names.

#### Resources:

1. Image: every image type that ImageIO can read in java

2. Music: mp3 files only.

3. Images: a folder of images with file name syntax: image_index.image_extension (e.g. 1.jpg, 10.png, etc.)

#### If Statements:

Syntax: 
```
[boolean] =>
[doSomething1]
[doSomething2]
[endCondition]
```
Boolean syntax:
```
variable_1 [>, <, ==, !=, >=, <=] variable_2
```

Example:
```
[1==1] =>
[title] 1 = 1
[size] 100 100
[endCondition]
```

#### Accessing fields

To get a field of a variable (like name of a person, choice index of a question), you use the following syntax:
```
[[variable] fieldName]
//Example
[[question] choice] //Return the choice index of the variable 'question'
[[player] name] //Return the name of the variable 'player'
```

#### Questions
1. Choice Questions
Syntax:
```
[question] [variableName = "variable_name_here"] [asker] question_here
[choice] [choiceIndex] choice1_here
[choice] [choiceIndex] choice2_here
[choice] [choiceIndex] choice3_here
(maximum 3 choices)
```
Example: (from the test code)
```
[question] [variableName = "q1"] [gf1] Are you free this weekend?
[choice] [1] Yes.
[choice] [2] No. 
```

You can later access the choice by using
```
[[questionVariableName] choice]
```

1. Choice Questions
Syntax:
```
[question] [variableName = "variable_name_here"] [asker] question_here
[choice] [choiceIndex] choice1_here
[choice] [choiceIndex] choice2_here
[choice] [choiceIndex] choice3_here
(maximum 3 choices)
```
Example: (from the test code)
```
[question][gf1] Are you free this weekend?
```

