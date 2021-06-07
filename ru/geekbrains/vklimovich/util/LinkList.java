package ru.geekbrains.vklimovich.util;

import java.util.*;

public class LinkList<E> implements Iterable<E>{
    ListNode<E> listTop;
    ListNode<E> listTail;
    int size;

    private static class ListNode<E>{
        E elem;
        ListNode<E> next;

        ListNode(E elem){
            this.elem = elem;
        }
    }

    public LinkList(){
        size = 0;
    }

    public int size(){
        return size;
    }

    public boolean isEmpty(){
        return size == 0;
    }

    public E get(int index){
        int i = 0;
        ListNode<E> curNode = listTop;
        while(curNode != null && i++ < index){
            curNode = curNode.next;
        }
        if(curNode != null)
            return curNode.elem;
        else
            throw new NoSuchElementException("index = " + index + ", size = " + size);
    }

    public E getFirst(){
        if(listTop == null)
            throw new NoSuchElementException("List is empty");
        return listTop.elem;
    }

    public E getLast(){
        if(listTail == null)
            throw new NoSuchElementException("List is empty");
        return listTail.elem;
    }

    public boolean add(E elem, int index){
        int i = 0;
        ListNode<E> curNode = listTop;
        ListNode<E> prevNode = null;
        while(curNode != null && i++ < index){
            prevNode = curNode;
            curNode = curNode.next;
        }
        if(i < index)
            throw new NoSuchElementException("index = " + index + ", size = " + size);
        ListNode<E> newNode = new ListNode<>(elem);
        newNode.next = curNode;
        if(prevNode == null)
            listTop = newNode;
        else
            prevNode.next = newNode;
        if(newNode.next == null)
            listTail = newNode;
        size++;
        return true;
    }

    public boolean addFirst(E elem){
        return add(elem, 0);
    }

    public boolean add(E elem){
        return addFirst(elem);
    }

    public boolean addLast(E elem){
        ListNode<E> newNode = new ListNode<>(elem);
        if(listTail != null)
            listTail.next = newNode;
        else
            listTop = newNode;
        listTail = newNode;
        size++;
        return true;
    }

    public boolean remove(int index){
        int i = 0;
        ListNode<E> curNode = listTop;
        ListNode<E> prevNode = null;
        while(curNode != null && i++ < index){
            prevNode = curNode;
            curNode = curNode.next;
        }
        if(i < index)
            throw new NoSuchElementException("index = " + index + ", size = " + size);
        if(prevNode == null){
            listTop = curNode.next;
            prevNode = listTop;
        }
        else
            prevNode.next = curNode.next;
        if(curNode.next == null)
            listTail = prevNode;
        size--;
        return true;
    }

    public boolean removeFirst(){
        return remove(0);
    }

    public boolean removeLast(){
        if(listTail == null)
            throw new NoSuchElementException("List is empty");
        return remove(size() - 1);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("{");
        if(!isEmpty()){
            Iterator<E> it = iterator();
            while(it.hasNext()){
                sb.append(it.next());
                sb.append(", ");
            }
            sb.delete(sb.length() - 2, sb.length());
        }
        sb.append("}");
        return sb.toString();
    }

    private class LstIterator implements Iterator<E>{
        ListNode<E> nextNode = listTop;
        int index = -1;

        @Override
        public boolean hasNext() {
            return nextNode != null;
        }

        @Override
        public E next() {
            if(nextNode == null)
                throw new NoSuchElementException();
            E elem = nextNode.elem;
            nextNode = nextNode.next;
            index++;
            return elem;
        }

        @Override
        public void remove() {
            if(index < 0)
                throw new NoSuchElementException();
            LinkList.this.remove(index);
        }
    }

    @Override
    public Iterator<E> iterator() {
        return new LstIterator();
    }
}
