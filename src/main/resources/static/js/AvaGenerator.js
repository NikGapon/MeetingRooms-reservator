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

    let canvas = document.createElement("canvas");
    canvas.width = 100;
    canvas.height = 100;
    let ctx = canvas.getContext("2d");

    document.body.appendChild(canvas);

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

        if (parseInt(n.toString()[2]) >= 5){
            ctx.beginPath();
            ctx.arc(50, 50, 25, 0, Math.PI * 2, true);


            ctx.fillStyle = `rgba(${r}, ${g}, ${b}, 1)`;
            ctx.fill();
            ctx.stroke();
        } else {
            ctx.beginPath();
            ctx.fillRect(25, 25, 50, 50);

            ctx.fill();
            ctx.stroke();
        }
        drawMatrix(m, generateRGBFromDigit((n.toString())[1]));
         rgb = generateRGBFromDigit((n.toString())[4]);
         r = rgb[0];
         g = rgb[1];
         b = rgb[2];



            ctx.beginPath();
            ctx.arc(50, 55, 20, 0, Math.PI, false);
            ctx.stroke();

    }

    function clear() {
        ctx.fillStyle = "#F8F8F8";
        ctx.fillRect(0, 0, canvas.width, canvas.height);
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