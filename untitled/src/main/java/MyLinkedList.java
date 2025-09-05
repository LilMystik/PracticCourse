import java.util.NoSuchElementException;

public class MyLinkedList<E> {

  private static class Node<E> {
    E data;
    Node<E> next;
    Node<E> prev;

    Node(E data) {
      this.data = data;
      this.next = null;
      this.prev = null;
    }
  }

  private Node<E> head;
  private Node<E> tail;
  private int size;

  public MyLinkedList() {
    head = null;
    tail = null;
    size = 0;
  }

  public int size() {
    return size;
  }

  public void addFirst(E element) {
    Node<E> newNode = new Node<>(element);

    if (head == null) {
      head = newNode;
      tail = newNode;
    } else {
      newNode.next = head;
      head.prev = newNode;
      head = newNode;
    }
    size++;
  }

  public void addLast(E element) {
    Node<E> newNode = new Node<>(element);

    if (tail == null) {
      head = newNode;
      tail = newNode;
    } else {
      tail.next = newNode;
      newNode.prev = tail;
      tail = newNode;
    }
    size++;
  }

  public void add(int index, E element) {
    if (index < 0 || index > size) {
      throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
    }

    if (index == 0) {
      addFirst(element);
    } else if (index == size) {
      addLast(element);
    } else {
      Node<E> current = getNode(index);
      Node<E> newNode = new Node<>(element);

      newNode.prev = current.prev;
      newNode.next = current;
      current.prev.next = newNode;
      current.prev = newNode;

      size++;
    }
  }

  public E getFirst() {
    if (head == null) {
      throw new NoSuchElementException("List is empty");
    }
    return head.data;
  }

  public E getLast() {
    if (tail == null) {
      throw new NoSuchElementException("List is empty");
    }
    return tail.data;
  }

  public E get(int index) {
    checkIndex(index);
    return getNode(index).data;
  }

  public E removeFirst() {
    if (head == null) {
      throw new NoSuchElementException("List is empty");
    }

    E removedData = head.data;

    if (head == tail) {
      head = null;
      tail = null;
    } else {
      head = head.next;
      head.prev = null;
    }

    size--;
    return removedData;
  }

  public E removeLast() {
    if (tail == null) {
      throw new NoSuchElementException("List is empty");
    }

    E removedData = tail.data;

    if (head == tail) {
      head = null;
      tail = null;
    } else {
      tail = tail.prev;
      tail.next = null;
    }

    size--;
    return removedData;
  }

  public E remove(int index) {
    checkIndex(index);

    if (index == 0) {
      return removeFirst();
    } else if (index == size - 1) {
      return removeLast();
    } else {
      Node<E> current = getNode(index);
      E removedData = current.data;

      current.prev.next = current.next;
      current.next.prev = current.prev;

      size--;
      return removedData;
    }
  }

  private Node<E> getNode(int index) {
    checkIndex(index);

    Node<E> current;
    if (index < size / 2) {
      // Start from head
      current = head;
      for (int i = 0; i < index; i++) {
        current = current.next;
      }
    } else {
      // Start from tail
      current = tail;
      for (int i = size - 1; i > index; i--) {
        current = current.prev;
      }
    }
    return current;
  }

  private void checkIndex(int index) {
    if (index < 0 || index >= size) {
      throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
    }
  }

}
