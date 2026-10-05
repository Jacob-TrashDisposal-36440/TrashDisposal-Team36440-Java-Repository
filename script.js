const scoreDisplay = document.querySelector("#score");
const pointButtons = document.querySelectorAll("[data-points]");
const resetButton = document.querySelector("#reset-score");
const challengeButton = document.querySelector("#new-challenge");
const challengeText = document.querySelector("#challenge-text");
const sparkButton = document.querySelector("#spark-button");
const cheer = document.querySelector("#cheer");

let score = 0;

const challenges = [
  "Score exactly 21 points.",
  "Reach 30 points using all three buttons.",
  "Score at least 25 points.",
  "Can you reach 17 points in five clicks?",
  "Make a score greater than 40 points."
];

const cheers = [
  "Sensors ready. Team ready. Let’s build!",
  "That idea deserves a test run!",
  "Tiny change, clear commit, big teamwork!",
  "Robot cheer activated. Beep boop hooray!"
];

function updateScore(points) {
  score += points;
  scoreDisplay.textContent = score;
  scoreDisplay.animate(
    [
      { transform: "scale(1)" },
      { transform: "scale(1.12)", color: "#facc15" },
      { transform: "scale(1)" }
    ],
    { duration: 260, easing: "ease-out" }
  );
}

pointButtons.forEach((button) => {
  button.addEventListener("click", () => {
    updateScore(Number(button.dataset.points));
  });
});

resetButton.addEventListener("click", () => {
  score = 0;
  scoreDisplay.textContent = score;
});

challengeButton.addEventListener("click", () => {
  const next = Math.floor(Math.random() * challenges.length);
  challengeText.textContent = challenges[next];
});

sparkButton.addEventListener("click", () => {
  const next = Math.floor(Math.random() * cheers.length);
  cheer.textContent = cheers[next];
});
