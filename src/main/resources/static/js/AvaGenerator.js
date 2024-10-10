async function sha256(message) {

    const msgBuffer = new TextEncoder("utf-8").encode(message);
    const hashBuffer = await crypto.subtle.digest("SHA-256", msgBuffer);
    const hashArray = Array.from(new Uint8Array(hashBuffer));
    const hashHex = hashArray.map(b => ("00" + b.toString(16)).slice(-2)).join("");
    return hashHex;
}
function generateRGBFromDigit(digit) {

    const red = (digit * 25 + 60) % 256;
    const green = (digit * 50 + 30) % 256;
    const blue = (digit * 75 + 90) % 256;

    return [red, green, blue];
}
function hexToDecimal(hex) {
    return BigInt(`0x${hex}`).toString();
}

(async function() {

    let canvas = document.getElementById("avatar_gen");
    canvas.width = 100;
    canvas.height = 100;
    let ctx = canvas.getContext("2d");


    async function generateAvatar(name) {
        let m = [
            [0, 0, 0, 0, 0],
            [0, 0, 0, 0, 0],
            [0, 0, 0, 0, 0],
            [0, 0, 0, 0, 0],
            [0, 0, 0, 0, 0]
        ];

        let hash = (await sha256(name));
        let n = hexToDecimal(hash);

        if (parseInt((n.toString())[0]) < 3) {
            for (let i = 0; i < m.length; i++) {
                for (let j = 0; j < m[i].length; j++) {
                    m[i][j] = (i + j) % 2 != 0 ? 0 : 1;
            }}
        }else if (parseInt(n.toString()[0]) > 6){
           for (let i = 0; i < m.length; i++) {
                for (let j = 0; j < m[i].length; j++) {
                      m[i][j] = (i + j) % 2 == 0 ? 0 : 1;
                }}
        } else if(parseInt(n.toString()[0]) == 5){
            for (let i = 0; i < m.length; i++) {
                                        for (let j = 0; j < m[i].length; j++) {
                                            m[i][j] = 1;
                                        }
                                    }
        } else{
         for (let i = 0; i < m.length; i++) {
                            for (let j = 0; j < m[i].length; j++) {
                                m[i][j] = i % 2 == 0 ? 0 : 1;
                            }
                        }
        }

        console.log(n);
        drawMatrix(m, generateRGBFromDigit((n.toString())[1]));
        rgb = generateRGBFromDigit((n.toString())[3]);
        r = rgb[0];
        g = rgb[1];
        b = rgb[2];
        ctx.fillStyle = `rgba(${r}, ${g}, ${b}, 1)`;

        if (parseInt(n.toString()[2]) > 5){
            ctx.beginPath();
            ctx.arc(50, 50, 25, 0, Math.PI * 2, true);
            ctx.fill();
        } else if(parseInt(n.toString()[2]) < 5) {
            ctx.beginPath();
            ctx.fillRect(25, 25, 50, 50);
            ctx.fill();
        }else{
            ctx.beginPath();
                        ctx.fillRect(0, 25, 100, 50);
                        ctx.fill();
        }

         rgb = generateRGBFromDigit((n.toString())[4]);
         r = rgb[0];
         g = rgb[1];
         b = rgb[2];
                 ctx.fillStyle = `rgba(${r}, ${g}, ${b}, 1)`;

            if (parseInt(n.toString()[5]) < 3) {
                drawDiamond(40, 40, 8, 8);
                drawDiamond(60, 40, 8, 8);
            } else if (parseInt(n.toString()[5]) > 3 && parseInt(n.toString()[5]) < 6) {
                drawEye(50, 40, 7);

            } else {
                drawEye(35, 40, 5);
                drawEye(65, 40, 5);
            }


            if (parseInt(n.toString()[6]) > 5) {
                ctx.beginPath();
                ctx.arc(50, 50, 15, 0.25 * Math.PI, 0.75 * Math.PI, false);
                ctx.stroke();
            } else {
                    ctx.beginPath();
                            ctx.moveTo(50 - 20 / 2, 65);
                            ctx.lineTo(50 + 20 / 2, 65);
                            ctx.stroke();
            }

            rgb = generateRGBFromDigit((n.toString())[7]);
                     r = rgb[0];
                     g = rgb[1];
                     b = rgb[2];
                             ctx.fillStyle = `rgba(${r}, ${g}, ${b}, 1)`;
            if (parseInt(n.toString()[8]) < 4) {
                    drawCylinder(40, 10, 20, 15);
             } else if (parseInt(n.toString()[8]) > 7) {
                    drawConeHat(50, 5, 20, 25);
                }






    }

    function clear() {
        ctx.fillStyle = "#F8F8F8";
        ctx.fillRect(0, 0, canvas.width, canvas.height);
    }
    function drawDiamond(x, y, w, h) {
                ctx.beginPath();
                ctx.moveTo(x, y - h / 2);
                ctx.lineTo(x + w / 2, y);
                ctx.lineTo(x, y + h / 2);
                ctx.lineTo(x - w / 2, y);
                ctx.closePath();
                ctx.fill();
            }
            function drawEye(x, y, radius) {
                ctx.beginPath();
                ctx.arc(x, y, radius, 0, Math.PI * 2, true);
                ctx.closePath();
                ctx.fill();
                ctx.stroke();
            }
            function drawCylinder(x, y, width, height) {
                ctx.beginPath();
                ctx.rect(x, y, width, height);
                ctx.fill();
                ctx.stroke();
                ctx.beginPath();
                ctx.rect(x - 5, y + height, width + 10, 5);
                ctx.fill();
                ctx.stroke();
            }
               function drawConeHat(x, y, baseWidth, height) {
                ctx.beginPath();
                ctx.moveTo(x, y);
                ctx.lineTo(x - baseWidth / 2, y + height);
                ctx.lineTo(x + baseWidth / 2, y + height);
                ctx.closePath();
                ctx.fill();
                ctx.stroke();
            }

    function drawMatrix(m, rgb) {
        clear();

        r = rgb[0];
        g = rgb[1];
        b = rgb[2];

        ctx.fillStyle = `rgba(${r}, ${g}, ${b}, 1)`;

        for (let i = 0; i < m.length; i++) {
            for (let j = 0; j < m[i].length; j++) {
                if (m[i][j] === 1) {
                    ctx.fillRect(j * 20, i * 20, 20, 20);
                }
            }
        }
    }
    let name = window.nameForAvatar;
    await generateAvatar(name);

})();