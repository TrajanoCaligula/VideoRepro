
<!DOCTYPE html>
<html>
<head>
  <title>Lista de videos</title>
</head>
<body>
  <h1>Lista de videos</h1>

  <ul id="lista-videos"></ul>

  <script>
  window.onload = function() {
    var xhr = new XMLHttpRequest();
    xhr.open("GET", "/videos"); // Replace with your servlet mapping
    xhr.onload = function() {
      if (xhr.status === 200) { // Check for successful response
        var videos = JSON.parse(xhr.responseText);
        populateVideoList(videos); // Call function to populate list
      } else {
        console.error("Error fetching videos:", xhr.statusText);
      }
    };
    xhr.send();
  };

  function populateVideoList(videos) {
  var videoList = document.getElementById("lista-videos");
  videoList.innerHTML = ""; // Clear existing content

  for (var i = 0; i < videos.length; i++) {
    var video = videos[i];

    // Crea un elemento para la plantilla
    var videoElement = document.createElement("div");
    videoElement.innerHTML = `
      <video width="320" height="240" controls>
        <source src="${video.url}" type="video/mp4">
      </video>
      <h2>${video.titulo}</h2>
    `;

    // Agrega el elemento a la lista
    videoList.appendChild(videoElement);
    }
  }
</script>
</body>
</html>