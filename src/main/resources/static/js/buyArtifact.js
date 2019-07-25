function verify(price)
  {
      let totalCoins = document.getElementById("total-coins").innerHTML;

      if (Number(totalCoins) >= Number(price)){
      alert("Purchase successful")
      } else {
      alert("Insufficient funds")
      }

      location.reload(true);

  }