#In Project 3, you will create an engine for generating explorable worlds. This is a large design project that will require you and one partner to work through every stage of development from ideation to presentation. The goal of this project is to teach you how to handle a larger piece of code with little starter code in the hopes of emulating something like a product development cycle.

#Your task for the next 2 weeks is to design and implement a 2D tile-based world exploration engine. By “tile-based”, we mean the worlds you generate will consist of a 2D grid of tiles. By “world exploration engine” we mean that your software will build a world, which the user will be able to explore by walking around and interacting with objects in that world. Your world will have an overhead perspective. As an example of a much more sophisticated system than you will build, the NES game “Zelda II” is (sometimes) a tile based world exploration engine that happens to be a video game:

#The system you build can either use graphical tiles (as shown above), or it can use text based tiles, like the game shown below:

#We will provide a tile renderer, a small set of starter tiles, and the headers for a few required methods that must be implemented for your world engine and that will be used by the autograder. The project will have two major deadlines. By the first deadline, you should be able to generate random worlds that meet the criteria below. By the second deadline, a user should be able to explore and interact with the world.

#The major goal of this project is to give you a chance to attempt to manage the enormous complexity that comes with building a large system. Be warned: The system you build probably isn’t going to be that fun for users! Two weeks is simply not enough time, particularly for novice programmers. However, we do hope you will find it to be a fulfilling project, and the worlds you generate might even be beautiful.

#Phase 1: World Generation
#As mentioned above, the first goal of the project will be to write a world generator. The requirements for your world are listed below:

#The world must be a 2D grid, drawn using our tile engine. The tile engine is described in lab12.
#The world must be pseudorandomly generated. Pseudorandomness is discussed in lab 12.
#The generated world must include distinct rooms and hallways, though it may also include outdoor spaces.
#At least some rooms should be rectangular, though you may support other shapes as well.
#Your world generator must be capable of generating hallways that include turns (or equivalently, straight hallways that intersect).
#The world should contain a random number of rooms and hallways.
#The locations of the rooms and hallways should be random.
#The width and height of rooms should be random.
#The length of hallways should be random.
#Rooms and hallways must have walls that are visually distinct from floors. Walls and floors should be visually distinct from unused spaces.
#Rooms and hallways should be connected, i.e. there should not be gaps in the floor between adjacent rooms or hallways.
#The world should be substantially different each time, i.e. you should not have the same basic layout with easily predictable features
#As an example of a world that meets all of these requirements (click for higher resolution), see the image below. In this image, # represents walls, a dot represents floors, and there is also one golden colored wall segment that represents a locked door. All unused spaces are left blank.

#Once you’ve completed lab 12, you can start working on your world generation algorithm without reading or understanding the rest of the spec.

#It is very likely that you will end up throwing away your first world generation algorithm. This is normal! In real world systems, it is common to build several completely new versions before getting something you’re happy with. The room generation algorithm above was my 3rd one, and was ultimately much simpler than either of my first two.

#You’re welcome to search the web for cool world generation algorithms. You should not copy and paste code from existing games or graphical demos online, but you’re welcome to draw inspiration from code on the web. Make sure to cite your sources using @source tags. You can also try playing existing 2D tile based games for inspiration. Brogue is an example of a particularly elegant, beautiful game. Dwarf Fortress is an example of an incredibly byzantine, absurdly complex world generation engine.

#The Default Tileset and Tile Rendering Engine
#The tile rendering engine we provide takes in a 2D array of TETile objects and draws it to the screen. Let’s call this TETile[][] world for now. world[0][0] corresponds to the bottom left tile of the world. The first coordinate is the x coordinate, e.g. world[9][0] refers to the tile 9 spaces over to the right from the bottom left tile. The second coordinate is the y coordinate, and the value increases as we move upwards, e.g. world[0][5] is 5 tiles up from the bottom left tile. All values should be non-null, i.e. make sure to fill them all in before calling renderFrame. Make sure you understand the orientation of the world grid! If you’re unsure, write short sample programs that draw to the grid to deepen your understanding. If you mix up x vs. y or up vs. down, you’re going to have an incredibly confusing time debugging.

#We have provided a small set of default tiles in Tileset.java and these should serve as a good example of how to create TETile objects. We strongly recommend adding your own tiles as well.

#The tile engine also supports graphical tiles! To use graphical tiles, simply provide the filename of the tile as the fifth argument to the TETile constructor. Images must be 16 x 16, and should ideally be in PNG format. There are a large number of open source tilesets available online for tile based games. Feel free to use these. Note: Your github accounts are set up to reject files other than .txt or .java files. We will not have access to your tiles when running your code. Make sure to keep your own copy of your project somewhere else other than Github if you want to keep a copy of your project with graphics for archival purposes. Graphical tiles are not required.

#Any TETile objects you create should be given a unique character that other tile’s do not use. Even if you are using your own images for rendering the tile, each TETile should still have its own character representation.

#If you do not supply a filename or the file cannot be opened, then the tile engine will use the unicode character provided instead. This means that if someone else does not have the image file locally in the same location you specified, your world will still be displayed, but using unicode characters instead of textures you chose.

#The tile rendering engine relies on StdDraw. We recommend against using StdDraw commands like setXScale or setYScale unless you really know what you’re doing, as you may considerably alter or damage the a e s t h e t i c of the system otherwise.

##Starting Your Program
#Ultimately, your project must support both methods of receiving input, namely Core.Engine.interactWithKeyboard() method, and the other using the Core.Engine.interactWithInputSting(String s) method.

#For phase 1, your project does not need to support interactWithKeyboard() but it must support interactWithInputString(). Specifically, you should be able to handle an input of the format “N#######S” where each # is a digit and there can be an arbitrary number of #s. This corresponds to requesting a new world, providing a seed, and then pressing S to indicate that the seed has been completely entered.

#When your Core.Engine.interactWithKeyboard() method is run, your program must display a Main Menu that provides at LEAST the options to start a new world, load a previously saved world, and quit. The Main Menu should be fully navigable via the keyboard, using N for “new world”, L for “load world”, and Q for quit. You may include additional options or methods of navigation if you so choose.

#After pressing N for “new world”, the user should be prompted to enter a “random seed”, which is an integer of their choosing. This integer will be used to generate the world randomly (as described later and in lab 12). After the user has pressed the final number in their seed, they should press S to tell the system that they’ve entered the entire seed that they want. Your world generator should be able to handle any positive seed up to 9,223,372,036,854,775,807. There is no defined behavior for seeds larger than this.

#The behavior of the “Load” command is described later in this specification.

#If the system is instead started with Core.Engine.interactWithInputString(), no menu should be displayed and nothing should be drawn to the screen. The system should otherwise process the given String as if a human user was pressing the given keys using the Core.Engine.interactWithKeyboard() method. For example, if we call Core.Engine.interactWithInputString("N3412S"), your program should generate a world with seed 3412 and return the generated 2D tile array. Note that letters in the input string can be upper or lower case and your engine should be able to accept either keypress (ie. “N” and “n” should both initiate the process of world generation). You should NOT render any tiles or play any sound when using interactWithInputString().

#If you want to allow the user to have additional options, e.g. the ability to pick attributes of their character, specify world generation parameters, etc., you should create additional options. For example, you might add a fourth option “S” to the main menu for “select creature and create new world” if you want the user to be able to pick what sort of creature to play as. These additional options may have arbitrary behavior of your choosing, however, the behavior of N, L, and Q must be exactly as described in the spec!

##Phase 2: Interactivity
#In the second phase of the project, you’ll add the ability for the user to actually interact with the world, and will also add user interface (UI) elements to your world to make it feel more immersive and informative.

#The requirements for interactivity are as follows:

#The user must be able to control some sort of “avatar” that can moved around using the W, A, S, and D keys. Lab 13 covers how to include interactivity. By “avatar”, we just mean some sort of on screen representation controlled by the user. For example, in my project, I used an “@” that could be moved around.
#The avatar must be able to interact with the world in some way.
#Your system must be deterministic in that the same sequence of keypresses from the same seed must result in exactly the same behavior every time. It is OK if you use a pseudorandom generator, since the Random object is guaranteed to output the same random numbers every time.
#The only files you may create must have the suffix “.txt” (for example “savefile.txt”). You will get autograder issues if you do not do this.
#Optionally, you may also include game mechanics that allow the user to win or lose (see gold points below). Aside from these feature requirements, there will be a few technical requirements for your system, described in more detail below.

##UI (User Interface) Appearance
#After the user has entered a seed and pressed S, the world should be displayed with a user interface. The user interface of your project must include:

#A 2D grid of tiles showing the current state of the world.
#A “Heads Up Display” that provides additional information that maybe useful to the user. At the bare minimum, this should include Text that describes the tile currently under the mouse pointer.
#As an example of the bare minimum, the simple interface below displays a grid of tiles and a HUD that displays the description of the tile under the mouse pointer (click image for higher resolution):

#You may include additional features if you choose. In the example below (click image for higher resolution), as with the previous example, the mouse cursor is currently over a wall, so the HUD displays the text “wall” in the top right. However, this HUD also provides the user with 5 hearts representing the avatar’s “health”. Note that this world does not meet the requirements of the spec above, as it is a large erratic cavernous space, as opposed to rooms connected by hallways.

#As an example, the game below (click image for higher resolution) uses the GUI to list additional valid key presses, and provides more verbose information when the user mouses-over a tile (“You see grass-like fungus.”). The image shown below is a professional game, so we do not expect your project to have this level of detail (but we encourage you to try for some interesting visuals).

##UI Behavior
#After the world has been generated, the user must be in control of some sort of avatar that is displayed in the world. The user must be able to move up, left, down, and right using the W, A, S, and D keys, respectively. These keys may also do additional things, e.g. pushing objects. You may include additional keys in your engine.

#The system must behave pseudorandomly. That is, given a certain seed, the same set of key presses must yield the exact same results!

#In addition to movement keys, if the user enters “:Q”, the program should quit and save. The description of the saving (and loading) function is described in the next section. This command must immediately quit and save, and should require no further keypresses to complete, e.g. do not ask them if they are sure before quitting. We will call this single action of quitting and saving at the same time “quit/saving”.

#This project uses StdDraw to handle user input. This results in a couple of important limitations:

#StdDraw does not support key combinations. When we say “:Q”, we mean “:” followed by “Q”.
#Can only register key presses that result in a char. This means any unicode character will be fine but keys such as the arrow keys and escape will not work.
#On some computers, may not support holding down of keys without some significant modifications, i.e. you can’t hold down the e key and keep moving east. If you can figure out how to support holding down of keys in a way that is compatible with interactWithInputString, you’re welcome to do so.
#Because of the requirement that your system must handle String input (via interactWithInputString), your engine cannot make use of real time, i.e. your system cannot have any mechanic which depends on a certain amount of time passing in real life, since that would not be captured in an input string and would not lead to deterministic behavior when using that string vs. providing input with the keyboard. Keeping track of the number of turns that have elapsed is a perfectly reasonable mechanic, and might be an interesting thing to include in your world, e.g. maybe the world grows steadily darker in color with each step. You’re welcome to include other key presses like allowing the user to press space bar in order to wait one turn.

#If you’re having trouble getting started on how to implement user interaction, check out InputDemo.java for inspiration.

##Saving and Loading
#Sometimes, you’ll be exploring your world, and you suddenly notice that it’s time to go to 61B lecture. For times like those, being able to save your progress and load it later is very handy. Your system must have the ability to save the state of the world while exploring, as well as to subsequently load the world into the exact state it was in when last saved.

#When the user restarts byow.Core.Main and presses L, the world should be in exactly the same state as it was before the project was terminated. This state includes the state of the random number generator! More on this in the next section. In the case that a user attempts to load but there is no previous save, your system should simply quit and the UI interface should close with no errors produced.

#In the base requirements, the command “:Q” should save and completely terminate the program. This means an input string that contains “:Q” should not have any more characters after it and loading a world would require the program to be run again with an input string starting with “L”.

#If you’re having trouble getting started on how to implement saving and loading, check out SaveDemo.java for inspiration.

##Interacting With Input Strings and Phase 2
#Your Core.Engine.interactWithInputString(String s) must be able to handle input strings that include movement

#For example, the string “N543SWWWWAA” corresponds to the user creating a world with the seed 543, then moving up four times, then left twice. If we called Core.Engine.interactWithInputString("N543SWWWWAA"), your system would return a TETile[][] representing the world EXACTLY as it would be if we’d used interactWithKeyboard and typed these keys in manually. Since the system must be deterministic given a seed and a string of inputs, this will allow users to replay exactly what happened for a given sequence of inputs. This will also be handy for testing out your code, as well as for our autograder.

#Core.Engine.interactWithInputString(String s) must also be able to handle saving and loading in a replay string, e.g. “N25SDDWD:Q” would correspond to starting a new world with seed 25, then moving right, right, up, right, then quit/saving. The method would then return the 2D TETile[][] array at the time of save. If we then started the engine with the replay string “LDDDD”, we’d reload the world we just saved, move right four times, then return the 2D TETile[][] array after the fourth move.

#Your world should not change in any way between saves, i.e. the same exact TETile[][] should be returned by the last call to interactWithInputString for all of the following scenarios:

#interactWithInputString(N999SDDDWWWDDD)
#interactWithInputString(N999SDDD:Q), then interactWithInputString(LWWWDDD)
#interactWithInputString(N999SDDD:Q), then interactWithInputString(LWWW:Q), then interactWithInputString(LDDD:Q)
#interactWithInputString(N999SDDD:Q), then interactWithInputString(L:Q), then interactWithInputString(L:Q) then interactWithInputString(LWWWDDD)
#we then called interactWithInputString with input “L:Q”, we’d expect the exact same world state to be saved and returned as TETile[][] as with the previous call where we provided “LDDDD”.

#The return value of the interactWithInputString method should not depend on whether the input string ends with :Q or not. The only difference is whether the world state is saved or not as a side effect of the method.

#Your project must still meet the basic requirements described above! For example, if you allow the user to use mouse clicks, the project should still allow keyboard based movement!

##Primary Features:

#Create a system so that the tile renderer only displays tiles on the screen that are within the line of sight of the avatar
#Add the ability for light sources to affect how the world is rendered, with at least one light source that can be turned on and off somehow
#Add entities which chase the avatar/other entities by use of a search algorithm from class, with a toggle to display their projected path
#Create a system for “encounters”, where a new interface appears when the avatar interacts with entities in the world, returning the avatar to the original interface when the encounter ends (e.g. Pokémon)
#Add the ability for the user to “replay” their most recent save, visually displaying all of the actions taken since the last time a new world was created. This must result in the same final state as would occur if the user had loaded the most recent save.
#Add the ability for the user to change the perspective of their view (first-person, 2.5D, etc.)
Secondary Features:

#Add multiple save slots which also adds a new menu option and a new shortcut to save to slots other than slot 1. You should be careful to still support the default behavior of saving and loading in order to be consistent with the replay string requirements
#Add the ability to create a new world without closing and reopening the project, either as a special option you can press while exploring, or when you reach a “game over” state if you’ve turned your world into a game
#Add menu option to change avatar appearance
#Add a menu option to give your avatar a name with is displayed on the HUD.
#Add a menu option or randomly determine what the environment/theme of the world will be.
#Add a menu option to change all text in the interface to a different language. English should be the default and there should be a way to switch it back to English.
#Add support for mouse clicks on the main menu for anything that can be done with a keypress on the main menu
#Make your engine render using images instead of unicode characters
#Add cool music to your menu and/or exploration interface. Also add sound effects for interactions the avatar makes with the world
#Add a minimap somewhere which shows the entire map and the current avatar location. This feature is a lot more interesting if you also implement a map which is larger than the screen so that you are unable to see the entire map normally.
#Add ability to rotate the world
#Add a display of real date and time in the Heads Up Display
#Add support for movement with mouse clicks on any visible square. You’ll need to implement some sort of algorithm for pathfinding
#Add support for 2 users to interact at the same time. Should have two avatars on screen which can move around and have separate control schemes
#Add support for undoing a movement (even moves that occurred in a saved world before the current one was loaded). Undoing a movement should reset the world back to before the most recent keypress but should actually add to the replay string and not remove a character (ie. undo command should be logged in the replay string)
#This list is by no means comphrehensive of all the things you could do to satisfy Ambition points! If you have another idea for how you want to make your project really cool, fill out this form to submit your idea and how many points you think it should be worth. You will get confirmation if your idea is approved and it may be added to the Ambition list above as well. If you have multiple ideas, please fill out the form once per addition. We will link a list of approved ideas below this line as we approve them. You’re welcome to use these approved ideas as well.

##Requirements Summary
#A list of the requirements and restrictions on your project. Please note that this section does not substitute reading the entire spec since there are a lot of details which are not captured here.

#When using interactWithKeyboard, must have a menu screen that has New World, Load, and Quit options, navigable by keyboard.
#When entering New World, user should enter an integer seed followed by S key. Upon pressing S, the world should be generated and displayed.
#Must have pseudorandomly generated worlds/variety in worlds, i.e. the world should be different for every seed.
#Generated world must include all of the visual features described in phase 1 above.
#User must be able to move around in the world using W, A, S, and D keys.
#User must be able to press “:Q” to quit, and after starting the program up again, the L option on the main menu should load the world state exactly as it was before.
#All random events should be pseudorandom, i.e. gives deterministic behavior given a seed.
#Must be able to interact using interactWithInputString, and behavior other than accepting input and drawing to the screen should be identical to interactWithKeyboard.
#interactWithInputString must return a TETile[][] array of the world at the time after the last character in the string is processed.
#interactWithInputString must be able to handle saving and loading, just like interactWithKeyboard.
#Must use our TileEngine and StdDraw for displaying graphics.
#Must have a HUD which displays relevant information somewhere outside of the area displaying the world/tiles.
#HUD must display description of tile upon hovering over the tile.
#Must not use real life time, i.e. nothing should be moving if no input is being received.

