// Get the snake element
const snake = document.getElementById("snake");

// Initial position of the snake
let snakeX = 0;
let snakeY = 0;

// Update the position of the snake
function updateSnake() {
    snake.style.left = snakeX + "px";
    snake.style.top = snakeY + "px";
}

// Handle keyboard input
document.addEventListener("keydown", (event) => {
    // Arrow keys
    if (event.key === "ArrowUp") {
        snakeY -= 20;
    } else if (event.key === "ArrowDown") {
        snakeY += 20;
    } else if (event.key === "ArrowLeft") {
        snakeX -= 20;
    } else if (event.key === "ArrowRight") {
        snakeX += 20;
    }

    updateSnake();
});

// Initial update
updateSnake();
