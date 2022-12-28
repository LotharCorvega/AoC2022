package day19;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Stack;

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
		Materials m = (Materials)o;
		
		return
			this.ore == m.ore
			&& this.clay == m.clay
			&& this.obsidian == m.obsidian
			&& this.geodes == m.geodes;
	}
	
	@Override
	public int hashCode() {
	    int hash = 7;
	    
	    hash = 31 * ore;
	    hash = 31 * clay;
	    hash = 31 * obsidian;
	    hash = 31 * geodes;
	    
	    return hash;
	}

	@Override
	public String toString() {
		return "ore: " + ore + " clay: " + clay + " obsidian: " + obsidian;
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

	// Blueprint for robot building costs
	Blueprint blueprint;

	// Robots
	int oreRobotCount;
	int clayRobotCount;
	int obsidianRobotCount;
	int geodeRobotCount;

	// Max robot counts
	int maxOreRobotCount;
	int maxClayRobotCount;
	int maxObsidianRobotCount;

	// Mask for previous robot built
	int robotMask;

	public State(Blueprint blueprint) {
		this.materials = new Materials(0, 0, 0, 0);
		this.blueprint = blueprint;
	}

	public State(State state) {
		this.time = state.time;

		this.materials = new Materials(state.materials);

		this.blueprint = state.blueprint;

		this.oreRobotCount = state.oreRobotCount;
		this.clayRobotCount = state.clayRobotCount;
		this.obsidianRobotCount = state.obsidianRobotCount;
		this.geodeRobotCount = state.geodeRobotCount;

		this.maxOreRobotCount = state.maxOreRobotCount;
		this.maxClayRobotCount = state.maxClayRobotCount;
		this.maxObsidianRobotCount = state.maxObsidianRobotCount;
	}
	
	@Override
	public boolean equals(Object o) {
		State s = (State)o;
		
		return this.materials.equals(s.materials)
			&& this.oreRobotCount == s.oreRobotCount
			&& this.clayRobotCount == s.clayRobotCount
			&& this.obsidianRobotCount == s.obsidianRobotCount
			&& this.geodeRobotCount == s.geodeRobotCount;
	}
	
	@Override
	public int hashCode() {
	    int hash = 7;
	    
	    hash = 31 * hash + materials.hashCode();	    
	    hash = 31 * hash + oreRobotCount;
	    hash = 31 * hash + clayRobotCount;
	    hash = 31 * hash + obsidianRobotCount;
	    hash = 31 * hash + geodeRobotCount;
	    
	    return hash;
	}

	@Override
	public String toString() {
		return "(oreRo: " + oreRobotCount + " clayRo: " + clayRobotCount + " obRo: " + obsidianRobotCount + " geoRo: "
				+ geodeRobotCount + ")";
	}
}

public class first {
	static String split = "(Blueprint )|(: Each ore robot costs )|( ore. Each clay robot costs )|( ore. Each obsidian robot costs )|( ore and )|( clay. Each geode robot costs )|( ore and )|( obsidian.)";
	static int maxGeodes;

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

		for (Blueprint currentBlueprint : blueprints) {
			State startState = new State(currentBlueprint);
			startState.oreRobotCount = 1;

			startState.maxOreRobotCount = Math.max(currentBlueprint.oreRobotCost.ore,
					Math.max(currentBlueprint.clayRobotCost.ore,
							Math.max(currentBlueprint.obsidianRobotCost.ore, currentBlueprint.geodeRobotCost.ore)));
			startState.maxClayRobotCount = Math.max(currentBlueprint.oreRobotCost.clay,
					Math.max(currentBlueprint.clayRobotCost.clay,
							Math.max(currentBlueprint.obsidianRobotCost.clay, currentBlueprint.geodeRobotCost.clay)));
			startState.maxObsidianRobotCount = Math.max(currentBlueprint.oreRobotCost.obsidian, Math.max(
					currentBlueprint.clayRobotCost.obsidian,
					Math.max(currentBlueprint.obsidianRobotCost.obsidian, currentBlueprint.geodeRobotCost.obsidian)));

			Stack<State> S = new Stack<>();
			S.add(startState);

			while (!S.isEmpty()) {
				State currentState = S.pop();

				processState(currentState, S);
				// System.out.println(S.size());
			}

			System.out.println("MaxGeodes: " + maxGeodes);
			qualitySum += maxGeodes * currentBlueprint.ID;
			maxGeodes = 0;
		}

		System.out.println(qualitySum);
	}

	public static void processState(State state, Stack<State> S) {
		if (state.time >= 24) {
			// Update global variable
			maxGeodes = Math.max(maxGeodes, state.materials.geodes);
			return;
		}

		// Store newly mined materials
		int minedOre = state.oreRobotCount;
		int minedClay = state.clayRobotCount;
		int minedObsidian = state.obsidianRobotCount;
		int minedGeodes = state.geodeRobotCount;

		// Always build geode-robot if possible
		if (state.materials.canAfford(state.blueprint.geodeRobotCost)) {
			State newState = new State(state);

			newState.time++;
			newState.geodeRobotCount++;
			newState.materials.subtract(state.blueprint.geodeRobotCost);

			newState.materials.ore += minedOre;
			newState.materials.clay += minedClay;
			newState.materials.obsidian += minedObsidian;
			newState.materials.geodes += minedGeodes;

			newState.robotMask = 0;

			S.add(newState);
		} else {
			boolean builtObsidianRobot = false;
			boolean builtClayRobot = false;
			boolean builtOreRobot = false;

			// Obsidian-robot
			if ((state.robotMask & 1) == 0 && state.obsidianRobotCount < state.maxObsidianRobotCount
					&& state.materials.canAfford(state.blueprint.obsidianRobotCost)) {
				State newState = new State(state);

				newState.time++;
				newState.materials.subtract(state.blueprint.obsidianRobotCost);
				newState.obsidianRobotCount++;

				newState.materials.ore += minedOre;
				newState.materials.clay += minedClay;
				newState.materials.obsidian += minedObsidian;
				newState.materials.geodes += minedGeodes;

				newState.robotMask = 0;

				S.add(newState);

				state.robotMask = (state.robotMask | 1);
				builtObsidianRobot = true;
			}
			// Clay-robot
			if ((state.robotMask & 2) == 0 && state.clayRobotCount < state.maxClayRobotCount
					&& state.materials.canAfford(state.blueprint.clayRobotCost)) {
				State newState = new State(state);

				newState.time++;
				newState.materials.subtract(state.blueprint.clayRobotCost);
				newState.clayRobotCount++;

				newState.materials.ore += minedOre;
				newState.materials.clay += minedClay;
				newState.materials.obsidian += minedObsidian;
				newState.materials.geodes += minedGeodes;

				newState.robotMask = 0;

				S.add(newState);

				state.robotMask = (state.robotMask | 2);
				builtClayRobot = true;
			}

			// Ore-robot
			if ((state.robotMask & 4) == 0 && state.oreRobotCount < state.maxOreRobotCount
					&& state.materials.canAfford(state.blueprint.oreRobotCost)) {
				State newState = new State(state);

				newState.time++;
				newState.materials.subtract(state.blueprint.oreRobotCost);
				newState.oreRobotCount++;

				newState.materials.ore += minedOre;
				newState.materials.clay += minedClay;
				newState.materials.obsidian += minedObsidian;
				newState.materials.geodes += minedGeodes;

				newState.robotMask = 0;

				S.add(newState);

				state.robotMask = (state.robotMask | 4);
				builtOreRobot = true;
			}

			// No-robot
			if (!builtObsidianRobot || !builtClayRobot || !builtOreRobot) {
				State newState = new State(state);

				newState.time++;

				newState.materials.ore += minedOre;
				newState.materials.clay += minedClay;
				newState.materials.obsidian += minedObsidian;
				newState.materials.geodes += minedGeodes;

				S.add(newState);
			}
		}
	}
}
