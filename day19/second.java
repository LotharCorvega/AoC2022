package day19;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import java.util.Stack;

public class second {
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

			Set<State> H = new HashSet<>();
			H.add(startState);

			while (!S.isEmpty()) {
				State currentState = S.pop();
				processState(currentState, S, H);
				//System.out.println(currentState);
			}

			System.out.println("MaxGeodes: " + maxGeodes);
			qualitySum += maxGeodes * currentBlueprint.ID;
			maxGeodes = 0;
		}

		System.out.println(qualitySum);
	}

	public static void processState(State state, Stack<State> S, Set<State> H) {
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
		
		// Booleans for checking which robots were built
		boolean builtOreRobot = false;
		boolean builtClayRobot = false;
		boolean builtObsidianRobot = false;
		boolean builtGeodeRobot = false;

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

			if (!H.contains(newState)) {
				S.add(newState);
				H.add(newState);
				
				builtGeodeRobot = true;
			}

		}

		// Obsidian-robot
		if (state.obsidianRobotCount < state.maxObsidianRobotCount
				&& state.materials.canAfford(state.blueprint.obsidianRobotCost)) {
			State newState = new State(state);

			newState.time++;
			newState.materials.subtract(state.blueprint.obsidianRobotCost);
			newState.obsidianRobotCount++;

			newState.materials.ore += minedOre;
			newState.materials.clay += minedClay;
			newState.materials.obsidian += minedObsidian;
			newState.materials.geodes += minedGeodes;

			if (!H.contains(newState)) {
				S.add(newState);
				H.add(newState);

				builtObsidianRobot = true;
			}

		}
		// Clay-robot
		if (state.clayRobotCount < state.maxClayRobotCount
				&& state.materials.canAfford(state.blueprint.clayRobotCost)) {
			State newState = new State(state);

			newState.time++;
			newState.materials.subtract(state.blueprint.clayRobotCost);
			newState.clayRobotCount++;

			newState.materials.ore += minedOre;
			newState.materials.clay += minedClay;
			newState.materials.obsidian += minedObsidian;
			newState.materials.geodes += minedGeodes;

			if (!H.contains(newState)) {
				S.add(newState);
				H.add(newState);

				builtClayRobot = true;
			}

		}

		// Ore-robot
		if (state.oreRobotCount < state.maxOreRobotCount && state.materials.canAfford(state.blueprint.oreRobotCost)) {
			State newState = new State(state);

			newState.time++;
			newState.materials.subtract(state.blueprint.oreRobotCost);
			newState.oreRobotCount++;

			newState.materials.ore += minedOre;
			newState.materials.clay += minedClay;
			newState.materials.obsidian += minedObsidian;
			newState.materials.geodes += minedGeodes;

			if (!H.contains(newState)) {
				S.add(newState);
				H.add(newState);

				builtOreRobot = true;
			}

		}

		// No-robot
		if (true) {
			State newState = new State(state);

			newState.time++;

			newState.materials.ore += minedOre;
			newState.materials.clay += minedClay;
			newState.materials.obsidian += minedObsidian;
			newState.materials.geodes += minedGeodes;

			if (!H.contains(newState)) {
				S.add(newState);
				H.add(newState);
			}
		}

	}
}
