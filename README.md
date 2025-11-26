<!-- README.md for Synchonevo Clash -->
<p align="center">
  <img alt="logo" src="https://raw.githubusercontent.com/your-repo/synchonevo-clash/main/assets/logo.png" width="160" />
</p>

<h1 align="center">⚔️ Synchonevo Clash — Merge Tactics</h1>

<p align="center">
  <em>Turn-based merge strategy — recruit, merge, deploy, and defeat the bosses.</em>
</p>

<p align="center">
  <a href="#-objective">Objective</a> •
  <a href="#oop">OOP Principle</a> •
  <a href="#-features">Features</a> •
  <a href="#-how-to-run">How to run</a> •
  <a href="#-gameplay-mechanics">Mechanics</a> •
  <a href="#-dev-notes">Dev notes</a> •
  <a href="#-roadmap">Roadmap</a>
</p>

---

## 🔰 Objective

Assemble a powerful army by buying cards, deploying units on a **3×3 board**, and merging three identical units to upgrade their star level. Defeat progressively stronger bosses to advance stages.

---

## ✨ Highlights / Features

- Console-based, deterministic + randomised gameplay loop.  
- **3×3 battlefield** (hardcoded display) and a **9-card bench**.  
- **Board cap:** max **6 units** deployed at any time.  
- **Merge system:** 3 identical units/cards → upgrade star (1★ → 2★ → 3★).  
- **Cost scaling:** `scaledCost = baseCost + (star - 1) * 1.5`.  
- Turn order by **speed**; boss attacks row-targeted or single targets (skips empty cells).  
- Battle log and per-attack delay for readable combat replay.  
- Sell units for **half** their cost.  
- Auto-merge works across bench and board.

---

## 🧩 Project Structure

```
SynchonevoClash/
└── 📁 src/
    ├── 📁 Boss/
    │   ├── ☕ Boss.java
    │   └── ☕ BossFactory.java
    ├── 📁 Cards/
    │   ├── ☕ Card.java
    │   ├── ☕ Shop.java
    │   ├── ☕ UnitFactory.java
    │   └── ☕ UnitType.java
    ├── 📁 Main/
    │   ├── ☕ Game.java
    │   ├── ☕ Main.java
    │   └── ☕ Player.java
    ├── 📁 UI/
    │   └── ☕ Display.java
    ├── 📁 Units/
    │   ├── ☕ ArcherUnit.java
    │   ├── ☕ MageUnit.java
    │   ├── ☕ TankUnit.java
    │   ├── ☕ Unit.java
    │   └── ☕ WarriorUnit.java
    └── 📁 Utils/
        └── ☕ Utils.java

```

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

‧₊˚ ┊ **🧩 Object-Oriented Principles (OOP) in Synchonevo Clash)**  

## 💊 Encapsulation  
Encapsulation is applied throughout the game by keeping sensitive data hidden inside classes and exposing them only through getters/setters.  
For example:  
- `Player` keeps **HP**, **gold**, **energy**, and **board** private.  
- `Unit` keeps its **attack**, **defense**, **HP**, and **position** protected or private.  
- `Boss` stores its stats internally and exposes controlled interactions such as `takeDamage()` and `bossIsAlive()`.

By bundling data with the methods that operate on it, the system prevents accidental modification and maintains consistent game state.

---

## 💡 Abstraction  
Abstraction is used to hide complex logic behind simple, clean method calls.  
Examples include:  
- `Display` handles all drawing of the board, logs, and UI. The game never needs to know *how* it prints — it just calls `displayBoard()` or `displayBattle()`.  
- `Utils` abstracts repeated logic like delays, random generation, and formatting.  
- `UnitFactory` and `BossFactory` hide the complexity of creating units and bosses; the game simply calls `getUnit(type)` or `getBossByStage(stage)` without worrying about instantiation details.

This separation keeps the game flow readable and reduces clutter in the core logic.

---

## 🧬 Inheritance  
Inheritance is a major part of the game's structure.  
The base class:

### `Unit`  
is extended by specialized subclasses such as:  
- `ArcherUnit`  
- `MageUnit`  
- `TankUnit`  
- `WarriorUnit`

These subclasses inherit core stats and behaviors but override certain methods (e.g., damage multipliers, scaling, and unit-type-specific attributes).

This promotes code reuse and allows new unit types to be added easily without modifying existing logic.

---

## 🎭 Polymorphism  
Polymorphism is used extensively, especially during combat.

### Examples:
- Each subclass of `Unit` overrides the `attack()` method with its own behavior, but the battle loop simply calls `u.attack(boss)` — allowing different attack styles to activate depending on the unit type.  
- `UnitFactory` and `BossFactory` return objects of different subclasses but with the same base type, allowing the program to treat all units and bosses uniformly.  
- The battle sequence uses a list of `Unit` references while each actual unit acts according to its class-specific implementation.

This enables flexible behaviors while keeping the code clean and unified.

---

## 📌 How to Run the Program

### 1. Clone the Repository
```
git clone https://github.com/katniis/Synchonevo-Clash.git
cd Synchonevo-Clash
```
### 2. Compile the Java Files
```
javac -encoding UTF-8 src/cards/*.java src/boss/*.java src/units/*.java src/ui/*.java src/utils/*.java src/main/*.java
```
***This compiles all .java files into .class files in the bin/ directory.***

### 3. Run the Game
```
java -Dfile.encoding=UTF-8 -cp src main.Main
```
### 4. Optional: Using run.bat (Windows Only)
***Open the project folder.***
***Double-click run.bat or run it via the terminal:***
```
./run.bat
```
***The game will start, and you can follow on-screen prompts to play.***

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
