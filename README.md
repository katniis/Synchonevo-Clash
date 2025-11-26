# ⚔️ Synchonevo Clash  
### **Merge Tactics: Turn-Based Strategy Game (Java Edition)**

---

## 📌 1. Project Title
**Synchonevo Clash: Merge Tactics – Turn-Based Strategy Game**

---

## 📌 2. Description / Overview
*Synchonevo Clash* is a Java-based turn-based strategy game where players use a randomized deck of units to battle against an AI opponent.  
Players can **summon**, **merge**, and **upgrade** units to create powerful formations capable of defeating the enemy.  

The game focuses on:  
- Tactical merging  
- Energy management  
- Automated battle phases  
- Adaptive AI decision-making  

Your goal is to reduce the opponent’s HP to zero by making the most strategic play each round.

---

## 📌 3. OOP Concepts Applied

### 🔹 Encapsulation
Important variables such as HP, energy, stats, and card data are hidden within classes and accessed using getters/setters.

### 🔹 Inheritance
The **Units** folder contains subclasses like *Archer*, *Mage*, *Tank*, and *Warrior*, all extending a base **Unit** class.

### 🔹 Polymorphism
Each unit type can override methods such as attack behaviors or stat scaling.  
Factories (e.g., `UnitFactory`, `BossFactory`) also use polymorphism when generating object instances.

### 🔹 Abstraction
Systems like Display handling, Boss generation, Card creation, and utility functions are abstracted into separate classes to simplify the core game logic.

---

## 📌 4. Program Structure


SynchonevoClash/
│
├── Boss/
│ ├── Boss.java
│ └── BossFactory.java
│
├── Cards/
│ ├── Card.java
│ ├── Shop.java
│ ├── UnitFactory.java
│ └── UnitType.java
│
├── Main/
│ ├── Game.java
│ ├── Main.java
│ └── Player.java
│
├── UI/
│ └── Display.java
│
├── Units/
│ ├── ArcherUnit.java
│ ├── MageUnit.java
│ ├── TankUnit.java
│ ├── Unit.java
│ └── WarriorUnit.java
│
└── Utils/
└── Utils.java


### 📌 Description of Key Folders

#### **Boss/**
Contains classes for enemy boss behavior and object generation.  
- `Boss.java` – Defines boss stats and actions  
- `BossFactory.java` – Creates different boss types dynamically  

#### **Cards/**
Manages card creation, unit purchasing, and card classification.  
- `Card.java` – Represents a card drawn by the player  
- `Shop.java` – Purchases or rolls for units  
- `UnitFactory.java` – Creates unit objects  
- `UnitType.java` – Enum listing all unit types  

#### **Main/**
Handles game flow and primary logic.  
- `Main.java` – Entry point of the program  
- `Game.java` – Controls turns, battles, and win/loss conditions  
- `Player.java` – Stores energy, HP, and unit list  

#### **UI/**
Responsible for user interface output.  
- `Display.java` – Shows game menus, boards, actions, and updates  

#### **Units/**
Contains all playable unit classes.  
- `Unit.java` – Base class  
- `Archer`, `Mage`, `Tank`, `Warrior` – Specialized units with unique stat scaling  

#### **Utils/**
General-purpose helper functions.  
- `Utils.java` – Random generators, formatting, and shared utility methods  

---

## 📌 5. How to Run the Program (Java)

### ✔️ Step 1 — Open terminal in the project folder

### ✔️ Step 2 — Compile all Java files

### ✔️ Step 3 — Run the game

---

## 📌 6. Sample Output

---

## 📌 7. Author and Acknowledgement

### 👨‍💻 **Authors**
- **Elijah Oreste**  
- **Justine Catapang**  
- **Zaireh Macatangay**

### 🙏 **Acknowledgements**
We acknowledge and give thanks to:  
- Our **parents** for their support  
- Our **professor** for guidance  
- Our **peers** for feedback and assistance  
- **God** for strength, wisdom, and inspiration throughout this project

---

## 📌 8. Other Sections

### 🔮 Future Enhancements
- Add GUI using JavaFX or Swing  
- Implement special unit abilities (AOE, buffs, shield, healing)  
- Introduce PvP multiplayer mode  
- Add rarity tiers for units  
- Improve boss mechanics and add AI difficulty scaling  

### 📚 References
- Oracle Java Documentation  
- Object-Oriented Programming course materials  
- Turn-based game design references  

---

## 🎯 Objective
Defeat the opponent by assembling a powerful army through smart merging, energy management, and strategic decision-making.

---
