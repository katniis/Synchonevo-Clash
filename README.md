# ⚔️ Synchonevo Clash  
### **Merge Tactics: Turn-Based Strategy Game**

---

## 📌 1. Project Title
**Synchonevo Clash: Merge Tactics – Turn-Based Strategy Game**

---

## 📌 2. Description / Overview
*Synchonevo Clash* is a turn-based strategy game where players battle against a computer-controlled opponent using a randomized deck of units. Players summon, merge, and upgrade units to strengthen their army and defeat the enemy.  
The game emphasizes **strategy**, **energy management**, and **tactical merging** as players work toward reducing the opponent’s HP to zero. Each round introduces new card draws, requiring players to adapt their strategy dynamically.

---

## 📌 3. OOP Concepts Applied

### 🔹 Encapsulation  
Game data such as player HP, deck contents, and unit stats are stored inside classes with controlled access through methods. This ensures data integrity and prevents unwanted manipulation.

### 🔹 Inheritance  
All unit types inherit from a base **Unit** class. This allows shared properties (attack, defense, tier) while enabling unique stats and mechanics for unit variations.

### 🔹 Polymorphism  
Attack behavior, unit merging, and upgrade mechanics can be overridden in subclasses. This provides flexibility and allows different unit types to behave uniquely during combat.

### 🔹 Abstraction  
Complex systems such as battle resolution, card generation, and AI decision-making are implemented in dedicated classes. This hides implementation details and makes the game easier to maintain and extend.

---

## 📌 4. Program Structure
