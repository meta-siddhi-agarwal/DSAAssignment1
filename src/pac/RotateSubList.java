package pac;

import java.util.Scanner;

public class RotateSubList {
    // value of the node
    int val;
    // pointer to next node
    RotateSubList next;
    // scanner obj. for taking input
    static Scanner scannerObject = new Scanner(System.in);
    // no. of nodes in linked list
    static int nodesNumber;
    // position of left sub list, right sub list and number of times we need to
    // rotate
    static int leftPos, rightPos, stepsCount;
    // head of the linked list
    static RotateSubList head;

    /**
     * constructor for initializing a node
     * 
     * @param value-> of the node
     */
    RotateSubList(int val) {
        this.val = val;
    }

    public static void main(String[] args) {
        boolean isValid = true;

        while (isValid) {
            try {
                // get no. of nodes
                nodesNumber = getLinkedList();
                if (nodesNumber > 0) {
                    head = createLinkedList(nodesNumber);

                    // get left position of sub list
                    leftPos = getPos("left");

                    if (leftPos > 0 && leftPos <= nodesNumber) {
                        // get right position of sub list
                        rightPos = getPos("right");

                        if (rightPos >= leftPos && rightPos <= nodesNumber) {

                            // get no. of times we want to rotate
                            stepsCount = getStepsCount();

                            // rotate linked list
                            RotateSubList headOfRotatedList = rotateSubList(head);
                            printList(headOfRotatedList);
                            isValid = false;
                        } else {
                            System.out.println("Enter valid position");
                        }
                    } else {
                        System.out.println("Enter valid position");

                    }
                } else if (nodesNumber < 0) {
                    System.out.println("Please enter valid number of nodes.");
                } else {
                    System.out.println("No nodes in linked list");
                    isValid = false;
                }

            } catch (Exception e) {
                if (e.getMessage() == null)
                    System.out.println("Enter valid input");
                else
                    System.out.println(e.getMessage());
                scannerObject.nextLine();
            }
        }
    }

    /**
     * will rotate sub list by provided places
     * 
     * @param head-> of the linked list
     * @return head->of the rotated list
     */
    static RotateSubList rotateSubList(RotateSubList head) {
        int nodesInSubList = rightPos - leftPos + 1;
        stepsCount = stepsCount % nodesInSubList;
        if (stepsCount > 0) {
            RotateSubList leftHead = head, rightHead = head;

            // get leftHead of the linked list
            for (int listIndx = 1; listIndx < leftPos; listIndx++) {
                leftHead = leftHead.next;
            }

            // get rightHead of the linked list
            for (int listIndx = 1; listIndx < rightPos; listIndx++) {
                rightHead = rightHead.next;
            }

            // node which points to left node
            RotateSubList previousLeftNode = head;

            // in case left node is the first node in linked list
            if (leftPos < 2)
                previousLeftNode = null;
            else {
                while (previousLeftNode.next != leftHead) {
                    previousLeftNode = previousLeftNode.next;
                }
            }

            // node which is the first node of rotated sub list
            RotateSubList rotatedSubListHead = leftHead;

            for (int listIndex = 1; listIndex <= nodesInSubList - stepsCount; listIndex++) {
                rotatedSubListHead = rotatedSubListHead.next;
            }

            // node which points to new head of the rotated sub list
            RotateSubList prevSubHeadNode = head;
            while (prevSubHeadNode.next != rotatedSubListHead)
                prevSubHeadNode = prevSubHeadNode.next;

            if (previousLeftNode != null)
                previousLeftNode.next = rotatedSubListHead;
            else {
                head = rotatedSubListHead;
            }

            prevSubHeadNode.next = rightHead.next;
            rightHead.next = leftHead;
        }
        return head;
    }

    /**
     * will print list
     * 
     * @param head-> of the rotated linked list
     */
    static void printList(RotateSubList head) {
        RotateSubList tempHead = head;
        System.out.println("Resultant list is ");
        while (tempHead != null) {
            System.out.print(tempHead.val + " ");
            tempHead = tempHead.next;
        }
    }

    /**
     * will get no. of times we need to rotate the linked list
     * 
     * @return no. of times we need to rotate the linked list
     * @throws Exception in case count is invalid
     */
    static int getStepsCount() throws Exception {
        System.out.println("Enter no. of times you want to rotate sublist in clockwise direction");
        int numberOfTimes = scannerObject.nextInt();
        if (numberOfTimes < 0)
            throw new Exception("Please enter valid count");
        return numberOfTimes;
    }

    /**
     * will get position of sublist
     * 
     * @param msg-> denotes which position we want(left or right)
     * @return position of sublist
     */
    static int getPos(String msg) {
        System.out.println("Enter " + msg + " position of sublist");
        int leftPosition = scannerObject.nextInt();
        return leftPosition;
    }

    /**
     * will get no. of nodes in linked list
     * 
     * @return no. of nodes in linked list
     */
    static public int getLinkedList() {
        System.out.println("Enter no. of nodes in linked list");
        int nodesNumber = scannerObject.nextInt();
        return nodesNumber;
    }

    /**
     * will create a linked list
     * 
     * @param nodesNumber-> present in linked list
     * @return head of the linked list
     */
    static public RotateSubList createLinkedList(int nodesNumber) {
        RotateSubList node;
        System.out.println("Enter value of head");
        // initiallly we will set the first value as the head node
        int valueNode = scannerObject.nextInt();
        node = new RotateSubList(valueNode);
        RotateSubList head = node;
        RotateSubList tempHead = node;
        // then we will create other nodes
        for (int listIndex = 1; listIndex < nodesNumber; listIndex++) {
            System.out.println("Enter value of nodes");
            int value = scannerObject.nextInt();
            node = new RotateSubList(value);
            tempHead.next = node;
            tempHead = tempHead.next;
        }
        return head;
    }
}
