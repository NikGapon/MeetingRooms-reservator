async function sha256(message) {
    const msgBuffer = new TextEncoder("utf-8").encode(message);
    const hashBuffer = await crypto.subtle.digest("SHA-256", msgBuffer);
    const hashArray = Array.from(new Uint8Array(hashBuffer));
    const hashHex = hashArray.map(b => ("00" + b.toString(16)).slice(-2)).join("");
    return hashHex;
}

(async function() {
    let canvas = document.createElement("canvas");
    canvas.width = 250;
    canvas.height = 250;
    let ctx = canvas.getContext("2d");

    document.body.appendChild(canvas);

    // Function to generate avatar based on name
    async function generateAvatar(name) {
        let m = [
            [0, 0, 0, 0, 0],
            [0, 0, 0, 0, 0],
            [0, 0, 0, 0, 0],
            [0, 0, 0, 0, 0],
            [0, 0, 0, 0, 0]
        ];

        let hash = (await sha256(name)).substr(0, m.length * m[0].length);

        for (let i = 0; i < m.length; i++) {
            for (let j = 0; j < m[i].length; j++) {
                let n = parseInt(hash.substr(i * j + j, 1), 16);
                m[i][j] = n > 7 ? 0 : 1;
            }
        }

        // Make matrix symmetric
        for (let i = 0; i < m.length; i++) {
            for (let j = Math.round(m[i].length / 2), k = 2; j < m[i].length; j++, k += 2) {
                m[i][j] = m[i][j - k];
            }
        }

        drawMatrix(m);
    }

    function clear() {
        ctx.fillStyle = "#F8F8F8";
        ctx.fillRect(0, 0, canvas.width, canvas.height);
    }

    function drawMatrix(m) {
        clear();

        let r = Math.floor(Math.random() * 128 + 128);
        let g = Math.floor(Math.random() * 128 + 128);
        let b = Math.floor(Math.random() * 128 + 128);

        ctx.fillStyle = `rgba(${r}, ${g}, ${b}, 1)`;

        for (let i = 0; i < m.length; i++) {
            for (let j = 0; j < m[i].length; j++) {
                if (m[i][j] === 1) {
                    ctx.fillRect(j * 50, i * 50, 50, 50);
                }
            }
        }
    }

    // Generate avatar immediately on page load using nameForAvatar
    generateAvatar(nameForAvatar);

})();