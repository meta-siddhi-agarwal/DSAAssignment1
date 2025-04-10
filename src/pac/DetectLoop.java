package pac;

import java.util.InputMismatchException;
import java.util.Scanner;

//class for detecting loop in a linked list if present
public class DetectLoop {
    int value;
    DetectLoop next;

    /**
     * constructor for initializing a node
     * 
     * @param value-> of the node
     */
    public DetectLoop(int value) {
        this.value = value;
        this.next = null;
    }

    // scanner obj. for taking input
    static Scanner scannerObject = new Scanner(System.in);

    /**
     * will get no. of nodes in linked list
     * 
     * @return int-> no. of nodes in linked list
     */
    static public int getLinkedList() {
        System.out.println("Enter no. of nodes in linked list");
        int nodesNumber = scannerObject.nextInt();
        return nodesNumber;
    }

    /**
     * will create a linked list
     * 
     * @param nodesNumber-> no. of nodes in linked list
     * @return head of linked list
     */
    static public DetectLoop createLinkedList(int nodesNumber) {
        DetectLoop node;
        System.out.println("Enter value of head");
        // initiallly we will set the first value as the head node
        int valueNode = scannerObject.nextInt();
        node = new DetectLoop(valueNode);
        DetectLoop head = node;
        DetectLoop tempNode = node;
        // then we will create other nodes
        for (int listIndex = 1; listIndex < nodesNumber; listIndex++) {
            System.out.println("Enter value of nodes");
            int value = scannerObject.nextInt();
            node = new DetectLoop(value);
            tempNode.next = node;
            tempNode = tempNode.next;
        }
        return head;
    }

    public static void main(String[] args) {

        boolean isValid = true;
        while (isValid) {
            try {
                // get no. of nodes
                int nodesNumber = getLinkedList();
                if (nodesNumber > 0) {
                    DetectLoop head = createLinkedList(nodesNumber);
                    // get pos. of linked list if chosen yes
                    int posOfHead = getPosOfCycle(nodesNumber);

                    if (posOfHead >= 1) {
                        createCycle(head, posOfHead);
                        // check whether cycle is present or not
                        boolean isCycle = detectCycle(head);
                        isValid = false;
                        if (isCycle)
                            System.out.println("Cycle is present");
                        else
                            System.out.println("No cycle present");

                    } else if (posOfHead == -1) {
                        System.out.println("Program terminated successfully");
                        isValid = false;
                    }

                    else
                        System.out.println("Enter valid position");
                } else if (nodesNumber < 0) {
                    System.out.println("Please enter valid number of nodes.");
                } else {
                    System.out.println("No nodes in linked list");
                    isValid = false;
                }

            } catch (InputMismatchException e) {
                System.out.println("Enter valid input");
                scannerObject.nextLine();
            } catch (Exception e) {
                if (e.getMessage() != null)
                    System.out.println(e.getMessage());
                else
                    System.out.println("Enter valid input");
                scannerObject.nextLine();
            }
        }
    }

    /**
     * will detect whether there is a cycle present or not
     * 
     * @param head-> of the linked list
     * @return whether there is a cycle present or not
     */
    static boolean detectCycle(DetectLoop head) {
        DetectLoop slow = head;
        DetectLoop fast = head;
        while (fast != null || fast.next != null) {
            if (fast == slow)
                return true;
            slow = slow.next;
            fast = fast.next.next;
        }
        return false;
    }

    /**
     * will create a cycle in linked list
     * 
     * @param head->      of the linked list
     * @param posOfHead-> where we need to create a cycle
     */
    static public void createCycle(DetectLoop head, int posOfHead) {
        DetectLoop temp = head;
        while (temp.next != null) {
            temp = temp.next;
        }

        DetectLoop cycleNode = head;
        for (int cycleNodeIndex = 1; cycleNodeIndex < posOfHead; cycleNodeIndex++) {
            cycleNode = cycleNode.next;
        }

        temp.next = cycleNode;
    }

    /**
     * will get position where we need to create a cycle
     * 
     * @param nodesNumber-> no. of nodes in linked list
     * @return position where we need to create a cycle
     */
    static public int getPosOfCycle(int nodesNumber) throws Exception {

        System.out.println("Do you want to have cycle in linked list\n"
                + "Type yes or no");
        String choice = scannerObject.next();
        choice = choice.toLowerCase();

        if (choice.equals("yes")) {
            System.out.println("Enter at which position you want cycle\n" +
                    "Enter position from 1");
            int position = scannerObject.nextInt();
            if (position <= nodesNumber) {
                return position;
            } else
                return -1;

        } else if (choice.equals("no"))
            return -1;
        else
            throw new Exception("Enter valid choice");
    }
}
