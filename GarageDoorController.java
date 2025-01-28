public class GarageDoorController {
    private static final int FULLY_CLOSED = 0;
    private static final int FULLY_OPEN = 5;
    private static final char NO_EVENT = '.';
    private static final char BUTTON_PRESS = 'P';
    private static final char OBSTACLE = 'O';

    private int position = FULLY_CLOSED;
    private boolean isMoving = false;
    private boolean isOpeningDirection = true;

    public String processEvents(String input) {
        // Input validation
        if (input == null) {
            throw new IllegalArgumentException("Input sequence cannot be null");
        }
        if (input.isEmpty()) {
            throw new IllegalArgumentException("Input sequence cannot be empty");
        }

        // Validate input characters
        for (char c : input.toCharArray()) {
            if (c != NO_EVENT && c != BUTTON_PRESS && c != OBSTACLE) {
                throw new IllegalArgumentException(
                        "Invalid event character: " + c + ". Allowed characters are: ., P, O"
                );
            }
        }

        StringBuilder output = new StringBuilder();
        output.append(position);

        for (char event : input.toCharArray()) {
            // Process event
            switch (event) {
                case BUTTON_PRESS:
                    handleButtonPress();
                    break;
                case OBSTACLE:
                    handleObstacle();
                    break;
                case NO_EVENT:
                    if (isMoving) {
                        moveOnce();
                    }
                    break;
            }

            output.append(position);
        }

        return output.toString();
    }

    private void handleButtonPress() {
        // Edge case: Button pressed when door is already at limit
        if (!isMoving) {
            isMoving = true;
            // If at either limit, determine correct direction
            if (position == FULLY_CLOSED) {
                isOpeningDirection = true;
            } else if (position == FULLY_OPEN) {
                isOpeningDirection = false;
            } else {
                // If in middle, maintain current direction
                // This handles the case where door was stopped midway
            }
            moveOnce();
        } else {
            // Stop movement immediately
            isMoving = false;
        }
    }

    private void handleObstacle() {
        // Edge case: Obstacle detected when door is not moving
        if (!isMoving) {
            return;
        }

        // Edge case: Obstacle detected at limits
        if ((position == FULLY_OPEN && isOpeningDirection) ||
                (position == FULLY_CLOSED && !isOpeningDirection)) {
            isMoving = false;
            return;
        }

        // Normal case: Reverse direction
        isOpeningDirection = !isOpeningDirection;
        moveOnce();
    }

    private void moveOnce() {
        if (isOpeningDirection) {
            if (position < FULLY_OPEN) {
                position++;
                // Edge case: Reached fully open position
                if (position == FULLY_OPEN) {
                    isMoving = false;
                }
            }
        } else {
            if (position > FULLY_CLOSED) {
                position--;
                // Edge case: Reached fully closed position
                if (position == FULLY_CLOSED) {
                    isMoving = false;
                }
            }
        }
    }

    // Helper method to get current state (useful for testing)
    public String getCurrentState() {
        return String.format("Position: %d, Moving: %b, Direction: %s",
                position,
                isMoving,
                isOpeningDirection ? "Opening" : "Closing"
        );
    }

    // Comprehensive test method covering edge cases
    public static void main(String[] args) {
        GarageDoorController controller = new GarageDoorController();

        // Test Case 1: Basic functionality
        System.out.println("Test 1 - Basic operation:");
        System.out.println(controller.processEvents("..P...."));  // Should open partially

        // Test Case 2: Multiple button presses
        controller = new GarageDoorController();
        System.out.println("\nTest 2 - Multiple button presses:");
        System.out.println(controller.processEvents("..PP.."));  // Should start and stop

        // Test Case 3: Obstacle handling
        controller = new GarageDoorController();
        System.out.println("\nTest 3 - Obstacle detection:");
        System.out.println(controller.processEvents("..P..O.."));  // Should reverse direction

        // Test Case 4: Edge case - Button press at limits
        controller = new GarageDoorController();
        System.out.println("\nTest 4 - Button at limits:");
        System.out.println(controller.processEvents("..P....P")); // Open then close

        // Test Case 5: Edge case - Obstacle at limits
        controller = new GarageDoorController();
        System.out.println("\nTest 5 - Obstacle at limits:");
        System.out.println(controller.processEvents("..P....OP")); // Should handle obstacle at top

        // Test Case 6: Edge case - Rapid button presses
        controller = new GarageDoorController();
        System.out.println("\nTest 6 - Rapid button presses:");
        System.out.println(controller.processEvents("PPPP")); // Multiple rapid presses

        // Test Case 7: Input validation
        controller = new GarageDoorController();
        System.out.println("\nTest 7 - Input validation:");
        try {
            controller.processEvents("..X..");  // Invalid character
        } catch (IllegalArgumentException e) {
            System.out.println("Caught expected exception: " + e.getMessage());
        }
    }

}