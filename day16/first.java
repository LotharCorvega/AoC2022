package day16;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.Set;
import java.util.Stack;
import java.util.TreeMap;

class State {
	int visitedMask;
	int position;
	int minutes;
	int pressure;

	public State(int visitedMask, int position, int minutes, int pressure) {
		this.visitedMask = visitedMask;
		this.position = position;
		this.minutes = minutes;
		this.pressure = pressure;
	}

	public State(State state) {
		this.visitedMask = state.visitedMask;
		this.position = state.position;
		this.minutes = state.minutes;
		this.pressure = state.pressure;
	}

	@Override
	public boolean equals(Object o) {
		State s = (State) o;
		return this.visitedMask == s.visitedMask 
			&& this.position == s.position 
			&& this.minutes == s.minutes
			&& this.pressure == s.pressure;
	}

	@Override
	public int hashCode() {
		int hash = 7;

		hash = 31 * hash + visitedMask;
		hash = 31 * hash + position;
		hash = 31 * hash + minutes;
		hash = 31 * hash + pressure;

		return hash;
	}
}

public class first {
	static int maxPressure = 0;

	public static void main(String[] args) throws FileNotFoundException {
		Scanner scanner = new Scanner(new File("day16/input.txt"));

		Map<String, Integer> pressureMap = new TreeMap<>();
		Map<String, ArrayList<String>> neighborMap = new TreeMap<>();

		while (scanner.hasNextLine()) {
			String[] line = scanner.nextLine().split("(Valve )|( has flow rate=)|(; tunnels? leads? to valves? )|(, )");

			int pressure = Integer.parseInt(line[2]);
			ArrayList<String> neighbors = new ArrayList<String>();

			for (int i = 3; i < line.length; i++) {
				neighbors.add(line[i]);
			}

			pressureMap.put(line[1], pressure);
			neighborMap.put(line[1], neighbors);
		}

		// Do numbering and pressure
		Map<String, Integer> numbering = new TreeMap<>();
		ArrayList<Integer> pressure = new ArrayList<>();
		int index = 0;
		int valveCount = 0;

		for (String name : pressureMap.keySet()) {
			int currentPressure = pressureMap.get(name);
			if (currentPressure > 0) {
				numbering.put(name, index++);
				pressure.add(currentPressure);
			}
		}
		
		valveCount = index;

		for (String name : pressureMap.keySet()) {
			if (!numbering.containsKey(name)) {
				numbering.put(name, index++);
			}
		}

		// Floyd-Warshall
		int n = neighborMap.size();

		int[][] dist = new int[n][n];
		int[][] next = new int[n][n];

		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				dist[i][j] = n + 1;
				next[i][j] = -1;
			}
		}

		for (Entry<String, ArrayList<String>> entry : neighborMap.entrySet()) {
			for (String name : entry.getValue()) {
				int u = numbering.get(entry.getKey());
				int v = numbering.get(name);

				dist[u][v] = 1;
				next[u][v] = v;
			}
		}

		for (Integer v : numbering.values()) {
			dist[v][v] = 0;
			next[v][v] = v;
		}

		for (int k = 0; k < n; k++) {
			for (int i = 0; i < n; i++) {
				for (int j = 0; j < n; j++) {
					if (dist[i][j] > dist[i][k] + dist[k][j]) {
						dist[i][j] = dist[i][k] + dist[k][j];
						next[i][j] = next[i][k];
					}
				}
			}
		}

		// Set up stack
		Stack<State> states = new Stack<>();
		Set<State> visitedStates = new HashSet<>();
		states.add(new State(0, numbering.get("AA"), 0, 0));

		// Run
		while (!states.isEmpty()) {
			State currentState = states.pop();
			visitedStates.add(currentState);
			processState(currentState, states, visitedStates, valveCount, dist, pressure);
		}

		System.out.println(maxPressure);
	}

	public static void processState(State currentState, Stack<State> states, Set<State> visitedStates, int valveCount, int[][] graph, ArrayList<Integer> pressure) {
		boolean visitedAll = true;

		for (int i = 0; i < valveCount; i++) {
			if ((currentState.visitedMask & (1 << i)) == 0) {
				State newState = new State(currentState);
				newState.minutes += graph[newState.position][i] + 1;

				if (newState.minutes >= 30) {
					maxPressure = Math.max(maxPressure, newState.pressure);
					continue;
				}

				newState.visitedMask |= (1 << i);
				newState.position = i;
				newState.pressure += (30 - newState.minutes) * pressure.get(i);

				if (!visitedStates.contains(newState)) {
					states.add(newState);
				}

				visitedAll = false;
			}
		}

		if (visitedAll) {
			maxPressure = Math.max(maxPressure, currentState.pressure);
		}
	}
}
