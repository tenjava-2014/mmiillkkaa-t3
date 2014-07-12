mmRandoms: a ten.java submission
==============================

[![ten.java](https://cdn.mediacru.sh/hu4CJqRD7AiB.svg)](https://tenjava.com/)

This is a submission for the 2014 ten.java contest.

- __Theme:__ What random events can occur in Minecraft?
- __Time:__ Time 3 (7/12/2014 14:00 to 7/13/2014 00:00 UTC)
- __MC Version:__ 1.7.9 (latest Bukkit beta)
- __Stream URL:__ None

# Setup
After the plugin has been placed in the plugins folder and the configuration has been generated, you can run the command
`/setup` which will allow you to change a few things.

# Events

There are two types of events: Events which occur on an interval and events which occur as reactions to others.

These events will occur on the interval set in the configuration:

* Chicken Attack
  * 1-6 chickens appear and begin to attack the player.
* Stalker Derpy Pig
  * A pig appears.
  * When the pig is killed, you get a cat.
* Derpy Zombie
  * Has dirt on its head.
  * Drops a set item as a "reward."
  
These events occur as reactions to other in-game events:

* Giant
  * When lightning strikes a zombie, a giant appears.
* Cake
  * Added to world generation.
  * Cake spawns in world with a configurable chance.

# Configuration

Configuration options:

* `EventsPerHour`
  * The number of random evens that will occur each our.
  * Events occur on at a set rate.
  * Default: 1
* `DerpyZombie.DropItemStack`
  * Change nothing here. The in-game setup will help you with this.
  * Default: 1 Apple
* `PercentageChanceOfCake`
  * The chance of cake spawning in a chunk.
  * Default: 10% (10)