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

class State2 extends State {
	int positionElephant;
	
	int waitHuman;
	int waitElephant;

	public State2(int visitedMask, int positionHuman, int positionElephant, int minutes, int pressure) {
		super(visitedMask, positionHuman, minutes, pressure);
		this.positionElephant = positionElephant;
		
		this.waitHuman = 0;
		this.waitElephant = 0;
	}

	public State2(State2 state) {
		super(state);
		this.pressure = state.pressure;
		
		this.waitHuman = 0;
		this.waitElephant = 0;
	}

	@Override
	public boolean equals(Object o) {
		State2 s = (State2) o;
		return this.visitedMask == s.visitedMask 
			&& this.position == s.position 
			&& this.positionElephant == s.positionElephant
			&& this.minutes == s.minutes
			&& this.pressure == s.pressure;
	}

	@Override
	public int hashCode() {
		int hash = 7;

		hash = 31 * hash + visitedMask;
		hash = 31 * hash + position;
		hash = 31 * hash + positionElephant;
		hash = 31 * hash + minutes;
		hash = 31 * hash + pressure;

		return hash;
	}
	
	@Override
	public String toString() {
		return Integer.toBinaryString(visitedMask) + " hum" + position + " ele" + positionElephant + " min" + minutes + " press" + pressure;
	}
}

public class second2 {
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
		

		//System.out.println(valveCount);

		// Set up stack
		Stack<State2> states = new Stack<>();
		Set<State2> visitedStates = new HashSet<>();

		int u = numbering.get("AA");
		for (int vHuman = 0; vHuman < valveCount; vHuman++) {
			for (int vElephant = 0; vElephant < valveCount; vElephant++) {
				if (vHuman != vElephant) {				
					int minutesHuman = dist[u][vHuman] + 1;
					int minutesElephant = dist[u][vElephant] + 1;
					
					int currentPressureHuman = (26 - minutesHuman) * pressure.get(vHuman);
					int currentPressureElephant = (26 - minutesElephant) * pressure.get(vElephant);
					
					if (minutesHuman < minutesElephant) {
						State2 firstState = new State2(((1 << vHuman)), vHuman, u, minutesHuman, currentPressureHuman);						
						firstState.waitElephant = minutesHuman;						
						states.add(firstState);
					} else if (minutesHuman > minutesElephant) {
						State2 firstState = new State2(((1 << vElephant)), u, vElephant, minutesElephant, currentPressureElephant);
						firstState.waitHuman = minutesElephant;						
						states.add(firstState);
					} else {
						int mask = 0;
						mask |= (1 << vHuman);
						mask |= (1 << vElephant);
						
						State2 firstState = new State2(mask, vHuman, vElephant, minutesHuman, currentPressureHuman + currentPressureElephant);						
						states.add(firstState);
					}
				}				
			}
		}

		// Run
		while (!states.isEmpty()) {
			State2 currentState = states.pop();
			visitedStates.add(currentState);
			processState(currentState, states, visitedStates, valveCount, dist, pressure);
			//System.out.println(currentState);
		}

		System.out.println(maxPressure);
	}

	public static void processState(State2 currentState, Stack<State2> states, Set<State2> visitedStates, int valceCount, int[][] graph, ArrayList<Integer> pressure) {
		boolean visitedAll = true;
		
		for (int vHuman = 0; vHuman < valceCount; vHuman++) {
			for (int vElephant = 0; vElephant < valceCount; vElephant++) {
				if (vHuman != vElephant && (currentState.visitedMask & (1 << vHuman)) == 0 && (currentState.visitedMask & (1 << vElephant)) == 0) {					
					State2 newState = new State2(currentState);					
					
					int minutesHuman = Math.max(0, graph[currentState.position][vHuman] - currentState.waitHuman + 1);
					int minutesElephant = Math.max(0, graph[currentState.positionElephant][vElephant] - currentState.waitElephant + 1);
					
					int currentPressureHuman = (26 - minutesHuman) * pressure.get(vHuman);
					int currentPressureElephant = (26 - minutesElephant) * pressure.get(vElephant);
					
					boolean hum = false;
					boolean ele = false;
					
					if (minutesHuman < minutesElephant) {
						newState.minutes += minutesHuman;
						newState.waitHuman = 0;
						newState.waitElephant += minutesHuman;
						hum = true;
					} else if (minutesHuman > minutesElephant) {
						newState.minutes += minutesElephant;
						newState.waitHuman += minutesElephant;
						newState.waitElephant = 0;
						ele = true;
					} else {
						newState.minutes += minutesHuman;						
						newState.waitHuman = 0;
						newState.waitElephant = 0;
						hum = true;
						ele = true;
					}
					
					if (newState.minutes >= 26) {
						maxPressure = Math.max(maxPressure, newState.pressure);
						continue;
					}
					
					if (hum) {
						newState.visitedMask |= (1 << vHuman);
						newState.position = vHuman;
						newState.pressure += currentPressureHuman;
					}
					
					if (ele) {
						newState.visitedMask |= (1 << vElephant);
						newState.positionElephant = vElephant;
						newState.pressure += currentPressureElephant;
					}
					
					if (!visitedStates.contains(newState)) {
						states.add(newState);
					}

					visitedAll = false;
				}
			}
		}

		if (visitedAll) {
			if (maxPressure < currentState.pressure) {
				System.out.println(currentState.minutes + " : " + currentState.pressure);
			}
			
			maxPressure = Math.max(maxPressure, currentState.pressure);

		}
	}
}
