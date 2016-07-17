
package com.google.java;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

public class Graph {

	private final List<Integer>[] vertices;
	private final int size;

	public Graph (final int size) {
		this.size = size;
		this.vertices = new ArrayList[size];
		for (int i = 0; i < size; i++) {
			this.vertices[i] = new ArrayList<Integer>();
		}
	}

	public static void main (final String[] args) {

		final Graph graph = new Graph(13);
		graph.connect(0, 1);
		graph.connect(0, 5);
		graph.connect(0, 6);
		graph.connect(2, 0);
		graph.connect(2, 3);
		graph.connect(5, 4);
		graph.connect(3, 5);
		graph.connect(6, 4);
		graph.connect(7, 6);
		graph.connect(8, 7);
		graph.connect(6, 9);
		graph.connect(9, 10);
		graph.connect(9, 12);
		graph.connect(9, 11);
		graph.connect(11, 12);
		graph.print();

		final GraphSearch search = new GraphSearch(graph);
		search.DFS();
		search.BFS();

	}

	public static interface lambdaList<T> {
		public T getElementAt (int index);
	}

	static class GraphSearch {

		private final Graph graph;
		final HashSet<Integer> painted = new HashSet<Integer>();

		public GraphSearch (final Graph graph) {
			this.graph = graph;
		}

		private void reset () {
			this.painted.clear();
		}

		public void DFS () {
			this.reset();
			System.out.println("---[DFS]---------------");
			final int level = 0;
			for (int i = 0; i < this.graph.size; i++) {
				if (this.painted.contains(i)) {
					continue;
				}
				this.DFS(i, level);
			}
		}

		private void DFS (final int vertex, final int level) {
			this.paint(vertex, level);
			for (int childIndex = 0; childIndex < this.graph.getNumberOfChildren(vertex); childIndex++) {
				final int child = this.graph.getChildOf(vertex, childIndex);
				if (this.painted.contains(child)) {
					continue;
				}
				this.DFS(child, level + 1);
			}
		}

		public void BFS () {
			this.reset();
			System.out.println("---[BFS]---------------");
			final int level = 0;
			final LinkedList<Integer> toPaint = new LinkedList<Integer>();
			for (int i = 0; i < this.graph.size; i++) {
				if (this.painted.contains(i)) {
					continue;
				}
				toPaint.add(i);
				toPaint.add(-1);
				this.BFS(level, toPaint);
			}
		}

		private void BFS (int level, final LinkedList<Integer> queue) {
			while (queue.size() > 0) {
				final int vertex = queue.remove();
				if (vertex < 0) {
					level++;
					continue;
				}
				if (this.painted.contains(vertex)) {
					continue;
				}
				this.paint(vertex, level);
				this.addChildrenOf(vertex, queue);
				queue.add(-1);
			}
		}

		private void addChildrenOf (final int vertex, final LinkedList<Integer> queue) {
			for (int childIndex = this.graph.getNumberOfChildren(vertex) - 1; childIndex >= 0; childIndex--) {
				final int child = this.graph.getChildOf(vertex, childIndex);
				if (this.painted.contains(child)) {
					continue;
				}
				queue.add(child);
			}
		}

		private void paint (final int vertex, final int level) {
			this.painted.add(vertex);
			System.out.println(this.prefix("-", level) + ">" + vertex);
		}

		private String prefix (final String string, final int level) {
			String tmp = ""; // not using StringBuilder because of overkill
			for (int i = 0; i < level; i++) {
				tmp = tmp + string;
			}
			return tmp;
		}
	}

	private void print () {
		for (int i = 0; i < this.size; i++) {
			System.out.println("[" + i + "] -> " + this.vertices[i]);
		}
	}

	public List<Integer> listChildrenOf (final int vertex) {
		return this.vertices[vertex];
	}

	public int getChildOf (final int vertex, final int childIndex) {
		return this.vertices[vertex].get(childIndex);
	}

	public int getNumberOfChildren (final int vertex) {
		return this.vertices[vertex].size();
	}

	public int size () {
		return this.size;
	}

	public void connect (final int vertexFrom, final int vertexTo) {
		this.vertices[vertexFrom].add(vertexTo);
	}

}
