const xBias = 60;
const yBias = 220;
const scale = 16;

const pacman0 = {x: 43, y: 3, w: 13};
const pacman1 = {x: 282, y: 42, w: 32};
const pacmanLeft1 = {x: 3, y: 3, w: 13};
const pacmanLeft2 = {x: 23, y: 3, w: 13};
const pacmanRight1 = {x: 3, y: 23, w: 13};
const pacmanRight2 = {x: 23, y: 23, w: 13};
const pacmanUp1 = {x: 3, y: 43, w: 13};
const pacmanUp2 = {x: 23, y: 43, w: 13};
const pacmanDown1 = {x: 3, y: 63, w: 13};
const pacmanDown2 = {x: 23, y: 62, w: 13};

const redGhostUp0 = {x: 3, y: 83, w: 14};
const redGhostUp1 = {x: 23, y: 83, w: 14};
const redGhostDown0 = {x: 43, y: 83, w: 14};
const redGhostDown1 = {x: 63, y: 83, w: 14};
const redGhostLeft0 = {x: 83, y: 83, w: 14};
const redGhostLeft1 = {x: 103, y: 83, w: 14};
const redGhostRight0 = {x: 123, y: 83, w: 14};
const redGhostRight1 = {x: 143, y: 83, w: 14};
const pinkGhostUp0 = {x: 3, y: 103, w: 14};
const pinkGhostUp1 = {x: 23, y: 103, w: 14};
const pinkGhostDown0 = {x: 43, y: 103, w: 14};
const pinkGhostDown1 = {x: 63, y: 103, w: 14};
const pinkGhostLeft0 = {x: 83, y: 103, w: 14};
const pinkGhostLeft1 = {x: 103, y: 103, w: 14};
const pinkGhostRight0 = {x: 123, y: 103, w: 14};
const pinkGhostRight1 = {x: 143, y: 103, w: 14};
const cyanGhostUp0 = {x: 3, y: 123, w: 14};
const cyanGhostUp1 = {x: 23, y: 123, w: 14};
const cyanGhostDown0 = {x: 43, y: 123, w: 14};
const cyanGhostDown1 = {x: 63, y: 123, w: 14};
const cyanGhostLeft0 = {x: 83, y: 123, w: 14};
const cyanGhostLeft1 = {x: 103, y: 123, w: 14};
const cyanGhostRight0 = {x: 123, y: 123, w: 14};
const cyanGhostRight1 = {x: 143, y: 123, w: 14};
const orangeGhostUp0 = {x: 3, y: 143, w: 14};
const orangeGhostUp1 = {x: 23, y: 143, w: 14};
const orangeGhostDown0 = {x: 43, y: 143, w: 14};
const orangeGhostDown1 = {x: 63, y: 143, w: 14};
const orangeGhostLeft0 = {x: 83, y: 143, w: 14};
const orangeGhostLeft1 = {x: 103, y: 143, w: 14};
const orangeGhostRight0 = {x: 123, y: 143, w: 14};
const orangeGhostRight1 = {x: 143, y: 143, w: 14};
const blueGhost0 = {x: 3, y: 163, w: 14};
const blueGhost1 = {x: 23, y: 163, w: 14};
const whiteGhost0 = {x: 43, y: 163, w: 14};
const whiteGhost1 = {x: 63, y: 163, w: 14};
const deadGhostUp = {x: 3, y: 203, w: 14};
const deadGhostDown = {x: 23, y: 203, w: 14};
const deadGhostLeft = {x: 43, y: 203, w: 14};
const deadGhostRight = {x: 63, y: 203, w: 14};

const cherry = {x: 172, y: 164, w: 12};
const strawberry = {x: 172, y: 184, w: 12};
const orange = {x: 172, y: 204, w: 12};
const apple = {x: 172, y: 224, w: 12};

const ready = {x: 186, y: 2, w: 79, h: 7};
const over = {x: 13, y: 192, w: 79, h: 7};

const pacman = [
    [pacmanLeft1, pacmanLeft2, pacmanLeft1, pacman0],
    [pacmanRight1, pacmanRight2, pacmanRight1, pacman0],
    [pacmanUp1, pacmanUp2, pacmanUp1, pacman0],
    [pacmanDown1, pacmanDown2, pacmanDown1, pacman0]
];

const ghost = [
    [
        [redGhostLeft0, redGhostLeft1],
        [redGhostRight0, redGhostRight1],
        [redGhostUp0, redGhostUp1],
        [redGhostDown0, redGhostDown1]
    ],
    [
        [pinkGhostLeft0, pinkGhostLeft1],
        [pinkGhostRight0, pinkGhostRight1],
        [pinkGhostUp0, pinkGhostUp1],
        [pinkGhostDown0, pinkGhostDown1]
    ],
    [
        [cyanGhostLeft0, cyanGhostLeft1],
        [cyanGhostRight0, cyanGhostRight1],
        [cyanGhostUp0, cyanGhostUp1],
        [cyanGhostDown0, cyanGhostDown1]
    ],
    [
        [orangeGhostLeft0, orangeGhostLeft1],
        [orangeGhostRight0, orangeGhostRight1],
        [orangeGhostUp0, orangeGhostUp1],
        [orangeGhostDown0, orangeGhostDown1]
    ],
    [
        [blueGhost0, blueGhost1]
    ],
    [
        [whiteGhost0, whiteGhost1]
    ],
    [
        [deadGhostLeft],
        [deadGhostRight],
        [deadGhostUp],
        [deadGhostDown]
    ]
];

const fruit = [cherry, strawberry, orange, apple];

var pacmanCnt = 0;
var ghostCnt = 0;
var mode = 0;
var status = 0;
var level = 0;

function createGame(canvas) {
    let ctx = canvas.getContext("2d");
    let img = new Image();
    img.src = "images.png";
    let title = new Image();
    title.src = "title.jpg";

    let clear = function() {
        ctx.clearRect(0, 0, canvas.width, canvas.length);
    };

    let getDir = function(dir) {
        if (dir[0] == 0 && dir[1] == -1) {
            return 0;
        } else if (dir[0] == 0 && dir[1] == 1) {
            return 1;
        } else if (dir[0] == -1 && dir[1] == 0) {
            return 2;
        } else if (dir[0] == 1 && dir[1] == 0) {
            return 3;
        };
        return 0
    };

    let drawRoundRect = function(ctx, x, y, width, height, color, radius, fill, stroke) {
        if (typeof stroke === 'undefined') {
            stroke = true;
        }
        if (typeof radius === 'undefined') {
            radius = 5;
        }
        if (typeof radius === 'number') {
            radius = {tl: radius, tr: radius, br: radius, bl: radius};
        } else {
            var defaultRadius = {tl: 0, tr: 0, br: 0, bl: 0};
            for (var side in defaultRadius) {
                radius[side] = radius[side] || defaultRadius[side];
            }
        }
        ctx.strokeStyle = "yellow";
        ctx.beginPath();
        ctx.moveTo(x + radius.tl, y);
        ctx.lineTo(x + width - radius.tr, y);
        ctx.quadraticCurveTo(x + width, y, x + width, y + radius.tr);
        ctx.lineTo(x + width, y + height - radius.br);
        ctx.quadraticCurveTo(x + width, y + height, x + width - radius.br, y + height);
        ctx.lineTo(x + radius.bl, y + height);
        ctx.quadraticCurveTo(x, y + height, x, y + height - radius.bl);
        ctx.lineTo(x, y + radius.tl);
        ctx.quadraticCurveTo(x, y, x + radius.tl, y);
        ctx.closePath();
        if (fill) {
            ctx.fill();
        }
        if (stroke) {
            ctx.stroke();
        }
    };

    let drawWall = function(maze, i, j) {
        if (maze[i][j-1] == -1 && maze[i][j+1] == -1 && (maze[i-1][j] != -1 || maze[i+1][j] != -1)) {
            if (i == 13 && (j == 14 || j == 15)) {
                ctx.strokeStyle = "grey";
            } else {
                ctx.strokeStyle = "blue";
            }
            ctx.lineWidth = 2;
            ctx.beginPath();
            ctx.moveTo(j * scale + xBias - scale / 2, i * scale + yBias);
            ctx.lineTo(j * scale + xBias + scale / 2, i * scale + yBias);
            ctx.stroke();
        } else if (maze[i-1][j] == -1 && maze[i+1][j] == -1 && (maze[i][j-1] != -1 || maze[i][j+1] != -1)) {
            ctx.strokeStyle = "blue";
            ctx.lineWidth = 2;
            ctx.beginPath();
            ctx.moveTo(j * scale + xBias, i * scale + yBias - scale / 2);
            ctx.lineTo(j * scale + xBias, i * scale + yBias + scale / 2);
            ctx.stroke();
        } else if ((maze[i][j-1] == -1 && maze[i-1][j] == -1 && maze[i+1][j] != -1 && maze[i][j+1] != -1)
            || (maze[i][j-1] == -1 && maze[i-1][j] == -1 && maze[i+1][j] == -1 && maze[i][j+1] == -1 && maze[i-1][j-1] != -1)) {
            ctx.strokeStyle = "blue";
            ctx.lineWidth = 2;
            ctx.beginPath();
            ctx.arc(j * scale + xBias - scale / 2, i * scale + yBias - scale / 2, scale / 2, 0, 0.5 * Math.PI, false);
            ctx.stroke();
        } else if ((maze[i][j+1] == -1 && maze[i-1][j] == -1 && maze[i+1][j] != -1 && maze[i][j-1] != -1)
            || (maze[i][j-1] == -1 && maze[i-1][j] == -1 && maze[i+1][j] == -1 && maze[i][j+1] == -1 && maze[i-1][j+1] != -1)) {
            ctx.strokeStyle = "blue";
            ctx.lineWidth = 2;
            ctx.beginPath();
            ctx.arc(j * scale + xBias + scale / 2, i * scale + yBias - scale / 2, scale / 2, 0.5 * Math.PI, 1 * Math.PI, false);
            ctx.stroke();
        } else if ((maze[i][j+1] == -1 && maze[i+1][j] == -1 && maze[i-1][j] != -1 && maze[i][j-1] != -1)
            || (maze[i][j-1] == -1 && maze[i-1][j] == -1 && maze[i+1][j] == -1 && maze[i][j+1] == -1 && maze[i+1][j+1] != -1)) {
            ctx.strokeStyle = "blue";
            ctx.lineWidth = 2;
            ctx.beginPath();
            ctx.arc(j * scale + xBias + scale / 2, i * scale + yBias + scale / 2, scale / 2, 1 * Math.PI, 1.5 * Math.PI, false);
            ctx.stroke();
        } else if ((maze[i][j-1] == -1 && maze[i+1][j] == -1 && maze[i-1][j] != -1 && maze[i][j+1] != -1)
            || (maze[i][j-1] == -1 && maze[i-1][j] == -1 && maze[i+1][j] == -1 && maze[i][j+1] == -1 && maze[i+1][j-1] != -1)) {
            ctx.strokeStyle = "blue";
            ctx.lineWidth = 2;
            ctx.beginPath();
            ctx.arc(j * scale + xBias - scale / 2, i * scale + yBias + scale / 2, scale / 2, 1.5 * Math.PI, 2 * Math.PI, false);
            ctx.stroke();
        }
    };

    let drawFood = function(i, j) {
        ctx.fillStyle = "white";
        ctx.beginPath();
        ctx.arc(j * scale + xBias, i * scale + yBias, 2, 0, 2 * Math.PI, false);
        ctx.closePath();
        ctx.fill();
    };

    let drawPill = function(i, j) {
        if (parseInt(pacmanCnt / 2) == 0) {
            ctx.fillStyle = "white";
            ctx.beginPath();
            ctx.arc(j * scale + xBias, i * scale + yBias, 5, 0, 2 * Math.PI, false);
            ctx.closePath();
            ctx.fill();
        }
    };

    let drawTofu = function(i, j) {
        if (parseInt(pacmanCnt / 2) == 0) {
            ctx.fillStyle = "white";
            drawRoundRect(ctx, j * scale + xBias - 5, i * scale + yBias - 5, 10, 10, "white", 2, true);
            ctx.fill();
        }
    };

    let drawFruit = function(i, j) {
        if (status == 1) {
            let loc = fruit[Math.floor(Math.random() * level) % 4];
            ctx.drawImage(img, loc.x, loc.y, loc.w, loc.w, j * scale + xBias - 12, i * scale + yBias - 12, 25, 25);
        }
    };

    let drawMaze = function(maze) {
        for (let i = 1; i < maze.height - 1; ++ i) {
            for (let j = 1; j < maze.width - 1; ++ j) {
                if (maze.maze[i][j] == -1) {
                    drawWall(maze.maze, i, j);
                } else if (maze.maze[i][j] == 1) {
                    drawFood(i, j);
                } else if (maze.maze[i][j] == 2) {
                    drawPill(i, j);
                } else if (maze.maze[i][j] == 3) {
                    drawFruit(i, j);
                } else if (maze.maze[i][j] == 4) {
                    drawTofu(i, j);
                }
            }
        }
    };

    let drawBackground = function() {
        ctx.fillStyle = "black";
        ctx.fillRect(0, 0, canvas.width, canvas.height);
    };

    let drawInfo = function(info) {
        level = info.level;
        status = info.status;

        ctx.drawImage(title, xBias - 6, yBias - 140, 464, 80);

        ctx.font = "25px 'Comic Sans MS'";
        ctx.fillStyle = "yellow";
        ctx.fillText("Score: " + info.scores, xBias + 20, yBias);
        ctx.fillText("Level: " + info.level, xBias + 350, yBias);

        for (let i=0; i<info.numOfLives; ++i) {
            ctx.drawImage(img, pacman1.x, pacman1.y, pacman1.w, pacman1.w, xBias + 30 + i * 35, yBias + 510, 25, 25);
        }

        for (let i=0; i<info.level; ++i) {
            ctx.drawImage(img, fruit[i].x, fruit[i].y, fruit[i].w, fruit[i].w, xBias + 410 - i * 35, yBias + 510, 25, 25);
        }

        drawRoundRect(ctx, xBias + 170, yBias + 510, 125, 25);

        ctx.font = "20px 'Comic Sans MS'";
        ctx.fillStyle = "yellow";
        if (info.mode == 0) {
            ctx.fillText("Pill Mode", xBias + 190, yBias + 530);
        } else {
            ctx.fillText("Tofu Mode", xBias + 180, yBias + 530);
        }

        if (status == 0) {
            ctx.drawImage(img, ready.x, ready.y, ready.w, ready.h, 200, 500, 184, 18);
        } else if (status == 2) {
            ctx.drawImage(img, over.x, over.y, over.w, over.h, 220, 500, 144, 18);
            setTimeout(function () {
                $.get("/reset", function(data) {});
            }, 5000);
        }
    };

    let drawPacman = function(obj) {
        if (obj.isVisible) {
            let dir = getDir(obj.direction);
            let loc = pacman[dir][pacmanCnt];
            ctx.drawImage(img, loc.x, loc.y, loc.w, loc.w, obj.loc.y + xBias - 12, obj.loc.x + yBias - 12, 25, 25);
        } else {
            ctx.globalAlpha = 0.5;
            let dir = getDir(obj.direction);
            let loc = pacman[dir][pacmanCnt];
            ctx.drawImage(img, loc.x, loc.y, loc.w, loc.w, obj.loc.y + xBias - 12, obj.loc.x + yBias - 12, 25, 25);
            ctx.globalAlpha = 1;
        }
    };

    let drawGhost = function(obj) {
        if (obj.state == 0) {
            let dir = getDir(obj.direction);
            let loc = ghost[obj.color-1][dir][pacmanCnt % 2];
            ctx.drawImage(img, loc.x, loc.y, loc.w, loc.w, obj.loc.y + xBias - 12, obj.loc.x + yBias - 12, 25, 25);
        } else if (obj.state == 1) {
            let loc = ghost[4][0][pacmanCnt % 2];
            ctx.drawImage(img, loc.x, loc.y, loc.w, loc.w, obj.loc.y + xBias - 12, obj.loc.x + yBias - 12, 25, 25);
        } else if (obj.state == 2) {
            let loc = ghost[4 + parseInt(pacmanCnt / 2)][0][pacmanCnt % 2];
            ctx.drawImage(img, loc.x, loc.y, loc.w, loc.w, obj.loc.y + xBias - 12, obj.loc.x + yBias - 12, 25, 25);
        } else if (obj.state == 3) {
            let dir = getDir(obj.direction);
            let loc = ghost[6][dir][0];
            ctx.drawImage(img, loc.x, loc.y, loc.w, loc.w, obj.loc.y + xBias - 12, obj.loc.x + yBias - 12, 25, 25);
        }

    };

    let drawGame = function(data) {
        drawBackground();
        drawMaze(data[data.length - 1].listener);
        data.forEach(function (obj) {
            if (obj.propertyName == "gameInfo") {
                drawInfo(obj.listener);
            } else if (obj.propertyName == "thePacMan") {
                drawPacman(obj.listener);
            } else if (obj.propertyName == "theGhost") {
                drawGhost(obj.listener);
            }
        })
    };

    let update = function() {
        pacmanCnt = (pacmanCnt + 1) % 4;
        ghostCnt = (ghostCnt + 1) % 2;
        clear();
        $.get("/update", function(data) {
            drawGame(JSON.parse(data));
        });
    };

    let changeDir = function(event) {
        if (status == 0) {
            // status = 1;
            $.get("/start", function(data) {});
        } else if (event.key == "ArrowLeft") {
            $.post("/changeDirection", "LEFT", function () {});
        } else if (event.key == "ArrowRight") {
            $.post("/changeDirection", "RIGHT", function () {});
        } else if (event.key == "ArrowUp") {
            $.post("/changeDirection", "UP", function () {});
        } else if (event.key == "ArrowDown") {
            $.post("/changeDirection", "DOWN", function () {});
        }
    };

    canvas.addEventListener("click", function (event) {
        let x = event.clientX - canvas.offsetLeft;
        let y = event.clientY - canvas.offsetTop;
        if (status == 0 && x > xBias + 170 && x < xBias + 295 && y > yBias + 510 && y < yBias + 535) {
            $.get("/mode", function(data) {
                mode = parseInt(data);
            })
        }
    });

    document.onkeydown = changeDir;

    return {
        update: update,
        clear: clear
    };
}

window.onload = function () {
    let app = createGame(document.querySelector("canvas"));
    setInterval(app.update, 80);
    $.get("/reset", function(data) {});
};