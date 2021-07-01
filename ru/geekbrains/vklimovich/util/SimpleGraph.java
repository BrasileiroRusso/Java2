package ru.geekbrains.vklimovich.util;

import java.util.*;
import java.util.function.Consumer;

public class SimpleGraph<E> {
    private final Set<E> vertSet;
    private final Map<E, Set<E>> vecinos;

    public SimpleGraph(){
        vertSet = new HashSet<>();
        vecinos = new HashMap<>();
    }

    public boolean addVertex(E elem){
        if(vertSet.contains(elem))
            return false;
        vertSet.add(elem);
        vecinos.put(elem, new HashSet<>());
        return true;
    }

    public boolean addEdge(E vert1, E vert2){
        if(!vertSet.contains(vert1) || !vertSet.contains(vert2))
            return false;
        vecinos.get(vert1).add(vert2);
        vecinos.get(vert2).add(vert1);
        return true;
    }

    public void dfs(Consumer<E> vertAction, Runnable compAction){
        VStack<E> vertStack = new ArrayListStack<>();
        Set<E> verts = new HashSet<>(vertSet);
        Set<E> visitedVerts = new HashSet<>();

        while(!verts.isEmpty()){
            E vert = verts.iterator().next();
            vertStack.push(vert);
            visitedVerts.add(vert);
            vertAction.accept(vert);
            while(!vertStack.isEmpty()){
                E adjustVert = getAdjustVert(vertStack.peek(), visitedVerts);
                if(adjustVert == null)
                    vertStack.pop();
                else{
                    vertStack.push(adjustVert);
                    visitedVerts.add(adjustVert);
                    vertAction.accept(adjustVert);
                }
            }
            verts.removeAll(visitedVerts);
            compAction.run();
        }
    }

    public void dfs(){
        dfs(System.out::print, System.out::println);
    }

    public void bfs(Consumer<E> vertAction, Runnable compAction){
        VQueue<E> vertQueue = new ArrayQueue<>();
        Set<E> verts = new HashSet<>(vertSet);
        Set<E> visitedVerts = new HashSet<>();
        E adjustVert;

        while(!verts.isEmpty()){
            E vert = verts.iterator().next();
            vertQueue.offer(vert);
            visitedVerts.add(vert);
            vertAction.accept(vert);
            while(!vertQueue.isEmpty()){
                E curVert = vertQueue.remove();
                while((adjustVert = getAdjustVert(curVert, visitedVerts)) != null){
                    vertQueue.offer(adjustVert);
                    visitedVerts.add(adjustVert);
                    vertAction.accept(adjustVert);
                }
            }
            verts.removeAll(visitedVerts);
            compAction.run();
        }
    }

    public void bfs(){
        bfs(System.out::print, System.out::println);
    }

    private E getAdjustVert(E vert, Set<E> exclVerts){
        Set<E> verts = vecinos.get(vert);
        if(verts == null)
            return null;
        Iterator<E> it = verts.iterator();
        while(it.hasNext()){
            E curVert = it.next();
            if(!exclVerts.contains(curVert))
                return curVert;
        }
        return null;
    }
}
