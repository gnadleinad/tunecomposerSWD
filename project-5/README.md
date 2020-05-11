# Design Overview
Project 5 brought about two new design features, group and ungroup. In order to implement these new design features we created a Group class and a Moveable interface. Note and Group classes implemented the Moveable interface implementing crucial methods such as drag() and extend(). We were then able to alter our selected ArrayList data structure to hold Moveables not only Notes. By doing this, we followed the guideline that says to code to interfaces, not classes and gave our project the ability for dynamic method invocation. In addition, we had Groups and Note objects extend the rectangle class instead of having them include a rectangle object as a field, which note did in the previous iteration. After examining our code, we recognized that our EventHandler and TuneComposer classes were overfull and decided to split our EventHandler class into four classes. We left only the initialization of scene and loader in TuneComposer and split EventHandler into MainController, InstrumentSelectController, ComposerTrackController, and ComposerMenueController. Also, we separated our scene elements into separate panes using the notes pane as our data structure which holds all of our Note and Group objects. In addition, instrument assignment was refactored to be cleaner than the implementation in project 4.

# Strengths
Four elements are particularly elegant in the way that we approached our solution. First, our use of the Moveable interface allowed us to have multiple implementations of the same function for different objects. Plus, we were then able to store Groups and Notes as moveable objects in the selected ArrayList. Second, the use of multiple controllers while still having a main controller helped us only have one controller that interacted with the FXML while still keeping our code clean and organized. Something else that was greatly beneficial to our design was the extension of the Rectangle class by Groups and Notes. This was helpful because we then had access to all the methods and fields of the rectangle class and were able to store Note and Group objects in the notes_pane. This allowed us to use the width, x, and y fields as positions and durations for our notes in the MIDI. Finally, we replaced our notePosition map which kept track of all of our notes and used the different panes to hold different elements of our scene. The notes_pane acts as the data structure which now holds all of our Groups and Notes. Using the notes_pane as our data structure allowed us to eliminate any duplication of data and the need to keep track of unnecessary invariants. We also changed our methods to keep objects in a well-formed state and not both return and change an object at the same time.

# Limitations
Something inelegant about our solution is that since we extended the rectangle class and so many of the methods that we needed were final, we needed to implement our own methods for many of these functions which led to a slightly cluttered Moveable interface. Something else is that our comments and variable names are quite lacking. In the process of doing a major overhaul, much of the code was refactored however many of the functions lost their documentation in the process. Also, in several places within our code we must know what type of object we are dealing with, Group or Note, which means that we often need to check the name of the class we’re currently in and often typecast our objects. This requires a level of insight about what type of objects are of type Moveable and goes against the principle of using as broad of types as possible. It would be best if we could avoid in future implementations checking the type of the object we are currently in. 
 
# Collaboration
Group members collaborated through GitHub, email, and text. Zoe helped keep others in the loop as to the current status of the project, helped with bug fixes, and final touches/ cleanup on several coding sections. Charlie along with the help of Daniel pushed the project forward by writing almost all of the starter code for each section in order to obtain a minimum viable product, in addition to including an invaluable insight as to the inner workings and how they would affect the overall design of the code. Daniel also drove the project by keeping group members on task and creating the UML diagrams. Andrew worked diligently on several sections of the project refactoring the design to be efficient, clean, and beautiful. Andrew also served as our Midi expert. Trung helped in the refactoring for the separation of the FXML panes to include the use of a stack pane for different visual elements. The team worked well because many communicated clearly and had a desire to contribute which led to a great work ethic. Something that could be improved is the separation of work and commitment to group meeting time. We can accomplish this by starting to use the ‘Playing Poker’ module at the beginning of the project to distribute work in order to access our velocity as a team. 
