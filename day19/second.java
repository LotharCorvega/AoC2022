package day19;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;

public class second {
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

		int qualitySum = 1;

		for (int i = 0; i < 3; i++) {
			currentBlueprint = blueprints.get(i);

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

			for (int minute = 0; minute < 32; minute++) {
				Set<State> newStates = new HashSet<>();

				for (State currentState : states) {
					processState(currentState, newStates);
				}
				
				cleanStates(newStates, 2);
				states = newStates;
			}

			int maxGeodes = 0;

			for (State s : states) {
				maxGeodes = Math.max(maxGeodes, s.materials.geodes);
			}
			
			qualitySum *= maxGeodes;
		}

		System.out.println(qualitySum);
	}
	
	public static void cleanStates(Set<State> states, int tolerance) {
		int maxGeodes = 0;
		
		for (State s : states) {
			maxGeodes = Math.max(maxGeodes, s.materials.geodes);
		}
		
		Iterator<State> iterator = states.iterator();
		
		while(iterator.hasNext()) {
			if (iterator.next().materials.geodes <= maxGeodes - tolerance) {
				iterator.remove();
			}
		}
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

			newState.materials.ore += minedOre;
			newState.materials.clay += minedClay;
			newState.materials.obsidian += minedObsidian;
			newState.materials.geodes += minedGeodes;

			states.add(newState);
		}

		// No-robot
		{
			state.materials.ore += minedOre;
			state.materials.clay += minedClay;
			state.materials.obsidian += minedObsidian;
			state.materials.geodes += minedGeodes;

			states.add(state);
		}
	}
}