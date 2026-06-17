
        const today = new Date();
        

        const yyyy = today.getFullYear();
        const mm = String(today.getMonth() + 1).padStart(2, '0'); 
        const dd = String(today.getDate()).padStart(2, '0'); 

        const currentDate = `${yyyy}-${mm}-${dd}`;

        const inputFecha = document.getElementById('fechaNacimiento');
        inputFecha.setAttribute('min', currentDate)