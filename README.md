<!-- README.md for Synchonevo Clash -->
<p align="center">
  <img alt="logo" src="https://scontent.fmnl8-6.fna.fbcdn.net/v/t1.15752-9/589712811_1163984755396754_7336964656730225636_n.png?_nc_cat=100&ccb=1-7&_nc_sid=9f807c&_nc_eui2=AeGLy2hR6WM3G12gqTE9VRX-bvYLTdZ3pdlu9gtN1nel2a8ufswnraFiH-LTjsEOBHX4TYdT9XBUCf1L92Qgcj8D&_nc_ohc=RKEDx4Hmx3cQ7kNvwFCMbI2&_nc_oc=AdnkimxZgK7_CWfBGJJVIv53YzLTOeu_HRy3WT6rKhuHaQIteGJ3zbzUJVPztCZcMkRRsPAbH13_XLRYYpwgDKDJ&_nc_zt=23&_nc_ht=scontent.fmnl8-6.fna&oh=03_Q7cD3wFnAYnGFr7Advyc6SikwMt7DDK5fNhoGPvIGfNAam9CZg&oe=694F2D3B" width="160" />
</p>

<h1 align="center">⚔️ Synchonevo Clash — Merge Tactics</h1>

<p align="center">
  <em>Turn-based merge strategy — recruit, merge, deploy, and defeat the bosses.</em>
</p>

<p align="center">
  <a href="#-objective">Objective</a> •
  <a href="#-highlights--features">Features</a> •
  <a href="#-project-structure">Project Structure</a> •
  <a href="#-oop-principles">OOP Principles</a> •
  <a href="#-how-to-run-the-program">How to Run</a> •
  <a href="#-sample-output">Sample Output</a> •
  <a href="#-contributors">Contributors</a>
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
        ├── ☕ Utils.java
        └── ☕ AudioPlayer.java

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

## 🧩 OOP Principles  

## 💊 Encapsulation  
Encapsulation is used across the codebase by keeping internal state private or protected and exposing only controlled access methods.

### `Unit.java`
- Core stats (`hp`, `attack`, `star`, etc.) are **protected**, allowing subclass extension without exposing direct modification.
- Access is controlled through getters (`getHp`, `getAttack`, `isAlive`) and safe setters like `setStar`.

### `Boss.java`
- Uses **private fields** with read-only getters.
- Interaction happens only through methods like `bossTakeDamage()` and `bossIsAlive()`, preventing external mutation.

### Player / Game / Shop
- Game state (bench, board, shop inventory) is kept in **private collections**.
- Operations (purchasing, merging, combat) happen through methods, ensuring consistent and safe state updates.

Encapsulation keeps logic centralized and prevents invalid or uncontrolled data changes.

---

## 💡 Abstraction  
Abstraction is implemented in the game's `Units` package by defining a clear, generalized structure that all unit types follow. The core **abstract `Unit` class** contains shared fields, common behavior, and abstract methods that each subclass must fulfill.
For example: 
- `Unit` provides universal logic such as **HP handling**, **attack flow**, and **star scaling**.  
- Abstract methods like `baseHp()` and `baseAttack()` are implemented differently by each specific unit.  
- Concrete classes (`WarriorUnit`, `MageUnit`, `ArcherUnit`, `TankUnit`) only define their **unique stats** and any **specialized behavior** such as custom attack effects.

This separation allows the combat system and other game features to operate using the `Unit` type alone. The internal details of each subclass remain hidden, making the system cleaner, more flexible, and easy to expand with new units in the future.
 
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
Polymorphism allows different unit and boss types to be handled through shared base classes and factory patterns.

### UnitFactory
- Returns objects as **`Unit`** while creating concrete subclasses (`WarriorUnit`, `MageUnit`, `ArcherUnit`, `TankUnit`).
- Combat and game logic operate only on `Unit` references, enabling all unit types to be used interchangeably.

### BossFactory
- Produces different boss types but exposes them only through the **`Boss`** interface/API.
- Game logic doesn’t need to know the specific boss class.

### Abstract `Unit` Class
- Defines abstract stat providers (`baseHp`, `baseAttack`) and default behavior (`computeDamage`, `attack`, `setStar`).
- Subclasses override or extend behavior (custom effects, damage logic) while still fitting into the same battle flow.

Polymorphism ensures different unit types behave uniquely while being treated uniformly in the game systems.


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

## 📌 Sample Output
```
┌────────────────────────────────────────────────────────────────────────────────┐
│                        S Y N C H O N E V O   C L A S H                         │
├────────────────────────────────────────────────────────────────────────────────┤

                                   [1] Start
                                [2] How to Play
                                   [3] Credit
                                    [4] Exit

┌────────────────────────────────────────────────────────────────────────────────┐
               A turn-based merge & summon strategy auto-battler
├────────────────────────────────────────────────────────────────────────────────┤
  Enter:  
```
---

## 👥 Contributors

### 👨‍💻 **Authors**
- **Elijah Oreste** Game Designer
- **Justine Catapang** Lead Developer  
- **Zaireh Macatangay** Quality Assurance
---

### 🙏 **Acknowledgements**
We acknowledge and give thanks to:  
We sincerely express our gratitude to our instructor for the guidance and support provided throughout the completion of this project. We also extend our appreciation to our classmates and peers for their cooperation and encouragement during the development process.
- **God** for strength, wisdom, and inspiration throughout this project

---
