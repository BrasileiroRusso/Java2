package ru.geekbrains.vklimovich.util;

import java.util.*;

public class DoubleLinkList<E> implements Iterable<E>{
    ListNode<E> listTop;
    ListNode<E> listTail;
    int size;

    private static class ListNode<E>{
        E elem;
        ListNode<E> next;
        ListNode<E> previous;

        ListNode(E elem){
            this.elem = elem;
        }

        ListNode(E elem, ListNode<E> previous, ListNode<E> next){
            this.elem = elem;
            this.previous = previous;
            this.next = next;
        }
    }

    public DoubleLinkList(){
        size = 0;
    }

    public int size(){
        return size;
    }

    public boolean isEmpty(){
        return size == 0;
    }

    public E get(int index){
        LstIterator it = moveTo(index);
        return it.next();
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
        LstIterator it = moveTo(index);
        it.add(elem);
        return true;
    }

    public boolean addFirst(E elem){
        new LstIterator().add(elem);
        return true;
    }

    public boolean add(E elem){
        return addFirst(elem);
    }

    public boolean addLast(E elem){
        LstIterator it = new LstIterator();
        it.toEnd();
        it.add(elem);
        return true;
    }

    public boolean remove(int index){
        moveTo(index).remove();
        return true;
    }

    public boolean removeFirst(){
        LstIterator it = new LstIterator();
        it.next();
        it.remove();
        return true;
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
        //sb.append(" tail = " + listTail);
        return sb.toString();
    }

    private LstIterator moveTo(int index){
        if(index < 0 || index >= size())
            throw new NoSuchElementException();
        LstIterator it = new LstIterator();
        if(index <= (size() - 1)/2){
            for(int i = 0;i < index;i++)
                it.next();
        }
        else{
            it.toEnd();
            for(int i = size() - 1;i > index;i--)
                it.previous();
        }
        return it;
    }

    private class LstIterator implements ListIterator<E>{
        ListNode<E> nextNode;
        ListNode<E> prevNode;
        int index;

        private LstIterator(){
            nextNode = listTop;
            prevNode = null;
            index = -1;
        }

        private void toEnd(){
            nextNode = null;
            prevNode = listTail;
            index = size - 1;
        }

        @Override
        public boolean hasNext() {
            return nextNode != null;
        }

        @Override
        public E next() {
            if(nextNode == null)
                throw new NoSuchElementException();
            E elem = nextNode.elem;
            prevNode = nextNode;
            nextNode = nextNode.next;
            index++;
            return elem;
        }

        @Override
        public boolean hasPrevious() {
            return prevNode != null;
        }

        @Override
        public E previous() {
            if(prevNode == null)
                throw new NoSuchElementException();
            E elem = prevNode.elem;
            nextNode = prevNode;
            prevNode = prevNode.previous;
            index--;
            return elem;
        }

        @Override
        public int nextIndex() {
            if(nextNode == null)
                throw new IllegalStateException();
            return index+1;
        }

        @Override
        public int previousIndex() {
            if(prevNode == null)
                throw new IllegalStateException();
            return index;
        }

        @Override
        public void remove() {
            if(prevNode == null)
                throw new IllegalStateException();
            if(prevNode.previous != null)
                prevNode.previous.next = nextNode;
            else
                listTop = nextNode;
            if(nextNode != null)
                nextNode.previous = prevNode.previous;
            else
                listTail = prevNode.previous;
            prevNode = prevNode.previous;
            size--;
            index--;
        }

        @Override
        public void set(E e) {
            if(prevNode == null)
                throw new IllegalStateException();
            prevNode.elem = e;
        }

        @Override
        public void add(E e) {
            ListNode<E> newNode = new ListNode<>(e, prevNode, nextNode);
            //System.out.println("prevNode = " + prevNode + ", nextNode = " + nextNode);
            if(prevNode != null)
                prevNode.next = newNode;
            else
                listTop = newNode;
            if(nextNode != null)
                nextNode.previous = newNode;
            else
                listTail = newNode;
            //System.out.println("listTop = " + listTop + ", listTail = " + listTail);
            nextNode = newNode;
            size++;
        }
    }

    @Override
    public Iterator<E> iterator() {
        return new LstIterator();
    }

    public ListIterator<E> listIterator(){
        return new LstIterator();
    }
}
