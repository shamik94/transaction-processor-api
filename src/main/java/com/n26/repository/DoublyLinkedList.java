package com.n26.repository;

import com.n26.model.Transaction;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class DoublyLinkedList {

    private Node head;
    private Node tail;

    public DoublyLinkedList() {
        head = null;
        tail = null;
    }

    public void sortedInsert(Node newNode) {
        Node current;

        // If first node to be insetailed in doubly
        // linked list
        if (head == null) {
            head = newNode;
            tail = newNode;
            head.prev = null;
            return;
        }

        LocalDateTime newNodeTimestamp = newNode.data.getTimestamp();
        LocalDateTime headTimestamp = head.data.getTimestamp();
        LocalDateTime tailTimeStamp = head.data.getTimestamp();

        // If node to be inserted has value less than first node
        if (newNodeTimestamp.compareTo(headTimestamp) < 0) {
            newNode.prev = null;
            head.prev = newNode;
            newNode.next = head;
            head = newNode;
            return;
        }

        // If node to be inserted has value more than last node
        if (newNodeTimestamp.compareTo(tailTimeStamp) > 0) {
            newNode.prev = tail;
            tail.next = newNode;
            tail = newNode;
            return;
        }

        // Insert in the middle
        Node curr = head.next;
        LocalDateTime currTimeStamp = curr.data.getTimestamp();
        while (currTimeStamp.compareTo(newNodeTimestamp) < 0)
            curr = curr.next;

        // Insert new node before temp
        (curr.prev).next = newNode;
        newNode.prev = curr.prev;
        curr.prev = newNode;
        newNode.next = curr;
    }

    class Node {
        Transaction data;
        Node next, prev;

        public Node(Transaction data) {
            this.data = data;
            this.next = this.prev = null;
        }
    }
}
