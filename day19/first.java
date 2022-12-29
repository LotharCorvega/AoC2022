package day19;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

class Materials {
	int ore;
	int clay;
	int obsidian;
	int geodes;

	public Materials(int ore, int clay, int obsidian, int geodes) {
		this.ore = ore;
		this.clay = clay;
		this.obsidian = obsidian;
		this.geodes = geodes;
	}

	public Materials(Materials materials) {
		this.ore = materials.ore;
		this.clay = materials.clay;
		this.obsidian = materials.obsidian;
		this.geodes = materials.geodes;
	}

	public boolean canAfford(Materials cost) {
		return this.ore >= cost.ore && this.clay >= cost.clay && this.obsidian >= cost.obsidian
				&& this.geodes >= cost.geodes;
	}

	public void subtract(Materials materials) {
		this.ore -= materials.ore;
		this.clay -= materials.clay;
		this.obsidian -= materials.obsidian;
		this.geodes -= materials.geodes;
	}

	@Override
	public boolean equals(Object o) {
		Materials m = (Materials) o;
		return this.ore == m.ore
			&& this.clay == m.clay 
			&& this.obsidian == m.obsidian 
			&& this.geodes == m.geodes;
	}

	@Override
	public int hashCode() {
		int hash = 7;

		hash = 31 * hash + ore;
		hash = 31 * hash + clay;
		hash = 31 * hash + obsidian;
		hash = 31 * hash + geodes;

		return hash;
	}
}

class Blueprint {
	int ID;

	Materials oreRobotCost;
	Materials clayRobotCost;
	Materials obsidianRobotCost;
	Materials geodeRobotCost;

	public Blueprint(int ID) {
		this.ID = ID;
	}
}

class State {
	// Elapsed time
	int time;

	// Materials present
	Materials materials;

	// Robots
	int oreRobotCount;
	int clayRobotCount;
	int obsidianRobotCount;
	int geodeRobotCount;

	public State(Blueprint blueprint) {
		this.materials = new Materials(0, 0, 0, 0);
	}

	public State(State state) {
		this.time = state.time;

		this.materials = new Materials(state.materials);

		this.oreRobotCount = state.oreRobotCount;
		this.clayRobotCount = state.clayRobotCount;
		this.obsidianRobotCount = state.obsidianRobotCount;
		this.geodeRobotCount = state.geodeRobotCount;
	}

	@Override
	public boolean equals(Object o) {
		State s = (State) o;

		return this.materials.equals(s.materials) 
			&& this.time == s.time 
			&& this.oreRobotCount == s.oreRobotCount
			&& this.clayRobotCount == s.clayRobotCount 
			&& this.obsidianRobotCount == s.obsidianRobotCount
			&& this.geodeRobotCount == s.geodeRobotCount;
	}

	@Override
	public int hashCode() {
		int hash = 7;

		hash = 31 * hash + materials.hashCode();
		hash = 31 * hash + time;
		hash = 31 * hash + oreRobotCount;
		hash = 31 * hash + clayRobotCount;
		hash = 31 * hash + obsidianRobotCount;
		hash = 31 * hash + geodeRobotCount;

		return hash;
	}
}

public class first {
	static String split = "(Blueprint )|(: Each ore robot costs )|( ore. Each clay robot costs )|( ore. Each obsidian robot costs )|( ore and )|( clay. Each geode robot costs )|( ore and )|( obsidian.)";

	public static int maxObsidianRobotCount;
	public static int maxClayRobotCount;
	public static int maxOreRobotCount;

	static Blueprint currentBlueprint;

	public static void main(String[] args) throws FileNotFoundException {
		Scanner scanner = new Scanner(new File("day19/input.txt"));
		ArrayList<Blueprint> blueprints = new ArrayList<>();

		while (scanner.hasNextLine()) {
			String[] line = scanner.nextLine().split(split);

			Blueprint blueprint = new Blueprint(Integer.parseInt(line[1]));

			blueprint.oreRobotCost = new Materials(Integer.parseInt(line[2]), 0, 0, 0);
			blueprint.clayRobotCost = new Materials(Integer.parseInt(line[3]), 0, 0, 0);
			blueprint.obsidianRobotCost = new Materials(Integer.parseInt(line[4]), Integer.parseInt(line[5]), 0, 0);
			blueprint.geodeRobotCost = new Materials(Integer.parseInt(line[6]), 0, Integer.parseInt(line[7]), 0);

			blueprints.add(blueprint);
		}

		int qualitySum = 0;

		for (Blueprint b : blueprints) {
			currentBlueprint = b;

			State startState = new State(currentBlueprint);
			startState.oreRobotCount = 1;

			maxOreRobotCount = Math.max(currentBlueprint.oreRobotCost.ore, 
				Math.max(currentBlueprint.clayRobotCost.ore,
				Math.max(currentBlueprint.obsidianRobotCost.ore, currentBlueprint.geodeRobotCost.ore)));
			maxClayRobotCount = Math.max(currentBlueprint.oreRobotCost.clay,
				Math.max(currentBlueprint.clayRobotCost.clay,
				Math.max(currentBlueprint.obsidianRobotCost.clay, currentBlueprint.geodeRobotCost.clay)));
			maxObsidianRobotCount = Math.max(currentBlueprint.oreRobotCost.obsidian, 
				Math.max(currentBlueprint.clayRobotCost.obsidian,
				Math.max(currentBlueprint.obsidianRobotCost.obsidian, currentBlueprint.geodeRobotCost.obsidian)));

			Set<State> states = new HashSet<>();
			states.add(startState);

			for (int minute = 0; minute < 24; minute++) {
				Set<State> newStates = new HashSet<>();

				for (State currentState : states) {
					processState(currentState, newStates);
				}

				states = newStates;
			}

			int maxGeodes = 0;

			for (State s : states) {
				maxGeodes = Math.max(maxGeodes, s.materials.geodes);
			}

			System.out.println("Blueprint " + currentBlueprint.ID + ": " + maxGeodes + " geodes");
			qualitySum += maxGeodes * currentBlueprint.ID;
		}

		System.out.println(qualitySum);
	}

	public static void processState(State state, Set<State> states) {
		// Store newly mined materials
		int minedOre = state.oreRobotCount;
		int minedClay = state.clayRobotCount;
		int minedObsidian = state.obsidianRobotCount;
		int minedGeodes = state.geodeRobotCount;

		// Always build geode-robot if possible
		if (state.materials.canAfford(currentBlueprint.geodeRobotCost)) {
			State newState = new State(state);

			newState.materials.subtract(currentBlueprint.geodeRobotCost);
			newState.geodeRobotCount++;
			newState.time++;

			newState.materials.ore += minedOre;
			newState.materials.clay += minedClay;
			newState.materials.obsidian += minedObsidian;
			newState.materials.geodes += minedGeodes;

			states.add(newState);
		}

		// Obsidian-robot
		if (state.obsidianRobotCount < maxObsidianRobotCount && state.materials.canAfford(currentBlueprint.obsidianRobotCost)) {
			State newState = new State(state);

			newState.materials.subtract(currentBlueprint.obsidianRobotCost);
			newState.obsidianRobotCount++;
			newState.time++;

			newState.materials.ore += minedOre;
			newState.materials.clay += minedClay;
			newState.materials.obsidian += minedObsidian;
			newState.materials.geodes += minedGeodes;

			states.add(newState);
		}

		// Clay-robot
		if (state.clayRobotCount < maxClayRobotCount && state.materials.canAfford(currentBlueprint.clayRobotCost)) {
			State newState = new State(state);

			newState.materials.subtract(currentBlueprint.clayRobotCost);
			newState.clayRobotCount++;
			newState.time++;

			newState.materials.ore += minedOre;
			newState.materials.clay += minedClay;
			newState.materials.obsidian += minedObsidian;
			newState.materials.geodes += minedGeodes;

			states.add(newState);
		}

		// Ore-robot
		if (state.oreRobotCount < maxOreRobotCount && state.materials.canAfford(currentBlueprint.oreRobotCost)) {
			State newState = new State(state);

			newState.materials.subtract(currentBlueprint.oreRobotCost);
			newState.oreRobotCount++;
			newState.time++;

			newState.materials.ore += minedOre;
			newState.materials.clay += minedClay;
			newState.materials.obsidian += minedObsidian;
			newState.materials.geodes += minedGeodes;

			states.add(newState);
		}

		// No-robot
		{
			state.time++;

			state.materials.ore += minedOre;
			state.materials.clay += minedClay;
			state.materials.obsidian += minedObsidian;
			state.materials.geodes += minedGeodes;

			states.add(state);
		}
	}
}