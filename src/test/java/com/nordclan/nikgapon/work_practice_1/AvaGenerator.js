async function sha256(message) {
  var message = /*[#th:block th:utext="${messageforjs}"/]*/;

  const msgBuffer = new TextEncoder("utf-8").encode(message);

  const hashBuffer = await crypto.subtle.digest("SHA-256", msgBuffer);

  const hashArray = Array.from(new Uint8Array(hashBuffer));

  const hashHex = hashArray
    .map(b => ("00" + b.toString(16)).slice(-2))
    .join("");
  return hashHex;
}

(async function() {

  let input = document.querySelector("input");
  let canvas = document.createElement("canvas");
  canvas.width = 100;
  canvas.height = 100;
  let ctx = canvas.getContext("2d");

  document.body.appendChild(canvas);

  input.addEventListener("input", async function() {
    let m = new Array(100);

    for (let i = 0; i < m.length; i++) {
      m[i] = new Array(100);
      for (let j = 0; j < m[i].length; j++){
        m[i][j] = 0;
      }
    }


    let hash = (await sсha256(this.value)).substr(0, 10);

    for (let i = 0; i < m.length; i++) {
      for (let j = 0; j < m[i].length; j++) {
        let n = parseInt(hash.substr(i * j + j, 1), 16);
        m[i][j] = n > 7 ? 0 : 1;
      }
    }

    // make symetric
    for (let i = 0; i < m.length; i++) {
      for (let j = Math.round(m[i].length / 2), k = 2; j < m[i].length; j++, k += 2) {
        m[i][j] = m[i][j - k];
      }
    }

    drawMatrix(m);

  });

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
          ctx.fillRect(j * 1, i * 1, 1, 1);
        }
      }
    }
  }

  clear();

})();
