package com.n26.repository;

import com.n26.model.Transaction;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class DoublyLinkedList {

    private Node head;
    private Node tail;

    public DoublyLinkedList() {
        head = null;
        tail = null;
    }

    public void sortedInsert(Node newNode) {

        // If first node to be inserted in doubly
        // linked list
        if (head == null) {
            head = newNode;
            tail = newNode;
            head.prev = null;
            return;
        }

        LocalDateTime newNodeTimestamp = newNode.data.getTimestamp();
        LocalDateTime headTimestamp = head.data.getTimestamp();
        LocalDateTime tailTimeStamp = tail.data.getTimestamp();

        // If node to be inserted has value less than first node
        if (newNodeTimestamp.isBefore(headTimestamp)) {
            newNode.prev = null;
            head.prev = newNode;
            newNode.next = head;
            head = newNode;
            return;
        }

        // If node to be inserted has value more than last node
        if (newNodeTimestamp.isAfter(tailTimeStamp)) {
            newNode.prev = tail;
            tail.next = newNode;
            tail = newNode;
            return;
        }

        // Insert in the middle
        Node curr = head.next;
        while (curr.data.getTimestamp().isBefore(newNodeTimestamp))
            curr = curr.next;

        // Insert new node before temp
        (curr.prev).next = newNode;
        newNode.prev = curr.prev;
        curr.prev = newNode;
        newNode.next = curr;
    }

    public void deleteNodesWithTimestampLessThanEqualTo(LocalDateTime ldt) {
        Node curr = tail;

        while (curr.data.getTimestamp().isAfter(ldt)) {
            curr = curr.prev;
        }
    }

    /*public static void main(String args[])
    {
        Node node = new Node(Transaction.builder().timestamp(LocalDateTime.now()).build());
        sortedInsert(node);
        node = new Node(Transaction.builder().timestamp(LocalDateTime.now().minusDays(1)).build());
        sortedInsert(node);
        node = new Node(Transaction.builder().timestamp(LocalDateTime.now().minusHours(1)).build());
        sortedInsert(node);
        node = new Node(Transaction.builder().timestamp(LocalDateTime.now().minusSeconds(10)).build());
        sortedInsert(node);
        node = new Node(Transaction.builder().timestamp(LocalDateTime.now()).build());
        sortedInsert(node);

        System.out.println("Doubly linked list on printing from left to right");
        printList(head);
    }

    static void printList(Node temp)
    {
        while (temp != null)
        {
            System.out.println(temp.data.getTimestamp() + " ");
            temp = temp.next;
        }
    }*/

    class Node {
        Transaction data;
        Node next, prev;

        public Node(Transaction data) {
            this.data = data;
            this.next = this.prev = null;
        }
    }
}

/*
package com.n26.repository;

import com.n26.model.Transaction;

import java.time.LocalDateTime;
import java.util.PriorityQueue;

public class TransactionStore {

    static private PriorityQueue<Transaction> transactions;

    public TransactionStore () {
        transactions = new PriorityQueue<>((t1, t2) -> {
            if (t1.getTimestamp().isBefore(t2.getTimestamp())) {
                return -1;
            } else if (t1.getTimestamp().isAfter(t2.getTimestamp())) {
                return 1;
            } else {
                return 0;
            }
        });
    }

    static public void insertTransaction (Transaction t) {
        transactions.add(t);
    }

    static public void removeTransactionBeforeTimeStamp (LocalDateTime localDateTime) {
        if (transactions.isEmpty()) return;
        while (transactions.peek() != null && transactions.peek().getTimestamp().isBefore(localDateTime)) {
            transactions.poll();
        }
    }


    public static void main(String[] args)
    {
        transactions = new PriorityQueue<>((t1, t2) -> {
            if (t1.getTimestamp().isBefore(t2.getTimestamp())) {
                return -1;
            } else if (t1.getTimestamp().isAfter(t2.getTimestamp())) {
                return 1;
            } else {
                return 0;
            }
        });
        insertTransaction(Transaction.builder().timestamp(LocalDateTime.now()).build());
        insertTransaction(Transaction.builder().timestamp(LocalDateTime.now().minusDays(1)).build());;
        insertTransaction(Transaction.builder().timestamp(LocalDateTime.now().minusHours(1)).build());
        insertTransaction(Transaction.builder().timestamp(LocalDateTime.now().minusSeconds(10)).build());
        insertTransaction(Transaction.builder().timestamp(LocalDateTime.now().plusDays(10)).build());
        insertTransaction(Transaction.builder().timestamp(LocalDateTime.now().plusHours(10)).build());
        insertTransaction(Transaction.builder().timestamp(LocalDateTime.now().plusMinutes(10)).build());
        insertTransaction(Transaction.builder().timestamp(LocalDateTime.now()).build());

        removeTransactionBeforeTimeStamp(LocalDateTime.now());
        System.out.println("Printing Data");
        printList();
    }

    static void printList()
    {
        while (transactions.peek() != null ) {
            System.out.println(transactions.poll().getTimestamp());
        }
    }
}

 */
