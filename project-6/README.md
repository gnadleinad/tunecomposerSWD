# -project-6-team4

# Design Overview
To implement undo and redo we added a new interface to represent any action that is taken. This interface had three methods. It includes undo, redo and selectMovables. Undo and redo exist so that each implementation of the interface can handle the undo and redo functionality differently as they need to. The selectMovables method exists to select the notes that were previously selected before the latest undo. We added a new class for each type of action that implemented this interface. This included adding/removing notes, grouping/ungrouping, dragging, extending, and selecting. To use these classes we added a done and undone stack to our mainController. With this, we were able to push/pop back and forth between the stacks to retrive the action that was either most recently done or undone. After popping an action for example, we were able to then call the undo or redo for that action as it is implemented in that specific class. The only other difference to our project 5 solution is that when these actions are done, an instance of the action class is made. 

# Strengths
Our use of an interface is valuable because it allows us to utilize polymorphism as we did in project 5. This is important in that it follows the guideline to separate a class if some behavior does not apply to every object of the class. If every action was not a separate class, we would need different undo and redo functionality for each type of object in the one class and likely would need to use multiple conditonals to determine which one to do. Additionaly, using an interface allowed us to have a stack for action objects so every different type of action could be added to the done and undone stacks. 

# Limitations
One limitation or problem with our design is that for some of the actions, we store a number of fields that we need to keep track of. This is mostly an issue of readability. It is difficult to track each of these fields and how they change during the process of an undo or redo. If we continued with this design choice, it would be challenging to add new features as it would only involve more fields to track. 
 
# Collaboration
Group members collaborated through GitHub, email, and text. Zoe helped keep others in the loop as to the current status of the project, helped with bug fixes, and final touches/ cleanup on several coding sections. Additionally Zoe was resonsible for the initial design for this part of the project. Daniel also drove the project by keeping group members on task and creating the UML diagrams. Daniel also worked with Zoe to build the new interface and first classes for this project as well as make the important realization and implementation of the selectMovables method of the action classes. Andrew worked on several sections of the project helping with design to be efficient, clean, and beautiful. Andrew also worked to identify some final bugs that we didn't see orginally. Charlie worked on implementing the extend and move action classes and helped with other improvements and bugs. 

The team worked well given the busy time of year and short timeline for the final project. Team members took responsibility for different parts of the project and worked together to combine it all. We finished project 5 a little behind schedule, but were able to quickly get a start on project 6 right after the completion of the previous work and due to the refactoring done previously, were able to complete most of project 6 without major changes to what we already had. Something that again could be improved is the separation of work and commitment to group meeting time. Although this is the last project, in the future it will be worthwhile to consider using the ‘Playing Poker’ module at the beginning and then to clearly distribute work in order to access our velocity as a team. 
