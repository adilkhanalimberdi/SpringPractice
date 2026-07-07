async function convert() {
    const inputEl = document.getElementById("unit");
    const fromEl = document.querySelector("select[name='from']");
    const toEl = document.querySelector("select[name='to']");
    const resultEl = document.getElementById("result");
    const resultContainer = document.getElementById("result-container");

    if (inputEl == null) {
        return;
    }

    const resetStyles = () => {
        resultContainer.classList.remove(
            "bg-slate-200", "border-slate-300", "border-2",
            "bg-emerald-200", "border-emerald-300",
            "bg-rose-200", "border-rose-300"
        );
    };

    if (!inputEl.value) {
        resetStyles();
        resultEl.textContent = "Start entering to see the result";
        resultContainer.classList.add("bg-slate-200", "border-2", "border-slate-300");
        return;
    }

    const formData = {
        "value": inputEl.value.trim(),
        "from": fromEl.value,
        "to": toEl.value
    };

    try {
        const response = await fetch("/v1/api/convert", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(formData)
        });

        if (!response.ok) {
            const errorData = await response.json();
            const error = new Error(errorData.message || "Something wen wrong, please try later");
            error.details = errorData.errors;

            throw error;
        }

        const result = await response.json();
        const formatter = new Intl.NumberFormat('en-US', {
            minimumFractionDigits: 0,
            maximumFractionDigits: 8
        });

        resetStyles();
        resultEl.textContent = `Result: ${formatter.format(inputEl.value)} ${fromEl.value.toLowerCase()} = ${formatter.format(result.result)} ${toEl.value.toLowerCase()}`;
        resultContainer.classList.add("bg-emerald-200", "border-2", "border-emerald-300");
    } catch (err) {
        console.error("Conversion failed:", err);

        resetStyles();
        resultContainer.classList.add("bg-rose-200", "border-2", "border-rose-300");

        if (err.details && err.details.value) {
            resultEl.textContent = err.details.value;
        } else {
            resultEl.textContent = err.message;
        }
    }
}

document.addEventListener("DOMContentLoaded", () => {
    const formEl = document.getElementById("convert-form")
    const inputEl = document.getElementById("unit");
    const fromEl = document.querySelector("select[name='from']");
    const toEl = document.querySelector("select[name='to']");

    if (inputEl != null) {
        inputEl.addEventListener("input", convert);
        fromEl.addEventListener("change", convert);
        toEl.addEventListener("change", convert);

        formEl.addEventListener("submit", (event) => {
            event.preventDefault();
        });
    }

    convert();
});