<h1 align="center">🚗 Car Rental System</h1>

<p align="center">
  A clean, console-based <b>Car Rental System</b> built in core Java —<br/>
  manage cars, handle rentals, and generate slips, all from the terminal.
</p>

<p align="center">
  <img src="https://img.shields.io/badge/Java-17%2B-orange?style=flat-square&logo=openjdk&logoColor=white" alt="Java"/>
  <img src="https://img.shields.io/badge/Paradigm-OOP-blue?style=flat-square" alt="OOP"/>
  <img src="https://img.shields.io/badge/Interface-CLI-black?style=flat-square" alt="CLI"/>
  <img src="https://img.shields.io/badge/Status-Active-success?style=flat-square" alt="Status"/>
</p>

---

## 📖 Overview

A menu-driven rental management app that demonstrates **Object-Oriented Programming** and **CRUD operations** in Java — no frameworks, no external dependencies, just the core language and collections.

Built to practice clean class design, state management, and input handling in a real-world-shaped problem.

---

## ✨ Features

| | Feature | Description |
|:--:|---|---|
| ➕ | **Add Car** | Register a new car with ID, model, and daily price |
| ❌ | **Remove Car** | Delete a car from the inventory |
| 📋 | **View All Cars** | List every car with live availability status |
| 🔍 | **Search by ID** | Instant lookup using the unique car ID |
| 🔎 | **Search by Model** | Find cars matching a model name |
| 🚗 | **Rent a Car** | Mark a car as rented and block double-booking |
| 🔄 | **Return a Car** | Restore availability on return |
| 💰 | **Update Price** | Change the per-day rate anytime |
| 🧾 | **Rental Slip** | Generate a formatted billing summary |

---

## 🛠️ Tech Stack

| Layer | Choice |
|---|---|
| **Language** | Java |
| **Design** | OOP — encapsulation, classes & objects |
| **Storage** | `ArrayList` (in-memory collection) |
| **Input** | `Scanner` (console I/O) |

---

## 🧠 Architecture

```
┌──────────────────────┐
│        Main          │  ← menu loop, user input, routing
└──────────┬───────────┘
           │
┌──────────▼───────────┐
│  CarRentalSystem     │  ← business logic (add, rent, return, search)
└──────────┬───────────┘
           │
┌──────────▼───────────┐
│        Car           │  ← data model (id, model, price, isAvailable)
└──────────────────────┘
```

**`Car`** — the entity. Holds car ID, model name, price per day, and availability flag.

**`CarRentalSystem`** — the service layer. Owns the car list and exposes all operations: add, remove, search, rent, return, update price, display.

**`Main`** — the entry point. Renders the menu and delegates every action to the service layer.

---

## 📁 Project Structure

```
Car-rental-System/
├── README.md
└── src/
    └── Main.java
```

---

## ⚙️ Getting Started

**Prerequisites:** JDK 8 or above ([download](https://adoptium.net/))

```bash
# 1. Clone the repository
git clone https://github.com/gudduKumar548/Car-rental-System.git

# 2. Enter the project folder
cd Car-rental-System

# 3. Compile
javac -d out src/Main.java

# 4. Run
java -cp out Main
```

---

## 📋 Menu Preview

```
╔════════════════════════════════════╗
║      CAR RENTAL SYSTEM MENU        ║
╠════════════════════════════════════╣
║  1. Add Car                        ║
║  2. Remove Car                     ║
║  3. View All Cars                  ║
║  4. Search Car by ID               ║
║  5. Search Car by Model            ║
║  6. Rent a Car                     ║
║  7. Return a Car                   ║
║  8. Update Car Price               ║
║  0. Exit                           ║
╚════════════════════════════════════╝
Enter your choice:
```

---

## 📚 What I Learned

- Modelling real-world entities as Java classes
- Applying encapsulation and separation of concerns
- Managing collections with `ArrayList` (search, filter, remove)
- Handling user input safely in a menu-driven loop
- Structuring a small application into clear, single-responsibility layers

---

## 🚀 Roadmap

- [ ] Persist data with a database (MySQL / PostgreSQL)
- [ ] Add user authentication (admin vs customer)
- [ ] Maintain booking history with rental dates
- [ ] Auto-calculate total cost from rental duration
- [ ] Build a REST API layer with Spring Boot
- [ ] Ship a web or GUI front-end

---

## 🤝 Contributing

Contributions are welcome — fork the repo, create a feature branch, and open a pull request.

---

## 👨‍💻 Author

**Guddu Kumar**

[![GitHub](https://img.shields.io/badge/GitHub-gudduKumar548-181717?style=flat-square&logo=github)](https://github.com/gudduKumar548)

---

<p align="center">
  ⭐ If this project helped you, consider giving it a star!
</p>
