/* =====================================================================
   InsureFlow - AI Assistant Chatbot Component
   Floating Widget for Domain Knowledge, Database Querying & FAQs
   ===================================================================== */

document.addEventListener('DOMContentLoaded', () => {
    initChatbot();
});

function initChatbot() {
    const trigger = document.getElementById('chatbot-trigger');
    const widget = document.getElementById('chatbot-widget');
    const closeBtn = document.getElementById('chatbot-close');
    const form = document.getElementById('chatbot-form');
    const input = document.getElementById('chatbot-input');
    const messagesContainer = document.getElementById('chatbot-messages');
    const badge = document.getElementById('chatbot-badge');

    if (!trigger || !widget) return;

    // Toggle Chat Visibility
    trigger.addEventListener('click', () => {
        const isHidden = widget.classList.contains('hidden');
        if (isHidden) {
            widget.classList.remove('hidden');
            input.focus();
            if (badge) badge.style.display = 'none';
        } else {
            widget.classList.add('hidden');
        }
    });

    closeBtn?.addEventListener('click', () => {
        widget.classList.add('hidden');
    });

    // Handle Form Submit
    form?.addEventListener('submit', async (e) => {
        e.preventDefault();
        const text = input.value.trim();
        if (!text) return;

        input.value = '';
        appendUserMessage(text);
        showTypingIndicator();

        try {
            const role = window.currentRole || 'CUSTOMER';
            let userId = null;
            if (role === 'CUSTOMER') {
                userId = document.getElementById('active-customer-select')?.value;
            } else if (role === 'AGENT') {
                userId = document.getElementById('active-agent-select')?.value;
            }

            const res = await API.sendChatMessage({
                message: text,
                role: role,
                userId: userId ? parseInt(userId) : null,
                language: window.currentLanguage || localStorage.getItem('insureflow_lang') || 'en'
            });

            removeTypingIndicator();
            appendBotMessage(res.reply, res.suggestions, res.dataCard);
        } catch (err) {
            removeTypingIndicator();
            appendBotMessage("⚠️ Sorry, I encountered an error connecting to the server. Please try again.", ["Try again"]);
        }
    });
}

function appendUserMessage(text) {
    const messagesContainer = document.getElementById('chatbot-messages');
    if (!messagesContainer) return;

    const msgDiv = document.createElement('div');
    msgDiv.className = 'chat-message message-user';
    msgDiv.innerHTML = `
        <div class="message-content">${escapeHtml(text)}</div>
        <div class="message-time">${formatTime()}</div>
    `;
    messagesContainer.appendChild(msgDiv);
    scrollToBottom();
}

function appendBotMessage(replyText, suggestions = [], dataCard = null) {
    const messagesContainer = document.getElementById('chatbot-messages');
    if (!messagesContainer) return;

    const msgDiv = document.createElement('div');
    msgDiv.className = 'chat-message message-bot';

    let formattedContent = formatMarkdown(replyText);

    let html = `
        <div class="bot-avatar">IF</div>
        <div class="message-body">
            <div class="message-content">${formattedContent}</div>
    `;

    // Interactive Suggestions Chips
    if (suggestions && suggestions.length > 0) {
        html += `<div class="chatbot-suggestions">`;
        suggestions.forEach(s => {
            html += `<button type="button" class="suggestion-chip" onclick="handleSuggestionClick('${escapeHtml(s)}')">${escapeHtml(s)}</button>`;
        });
        html += `</div>`;
    }

    html += `<div class="message-time">${formatTime()}</div></div>`;

    msgDiv.innerHTML = html;
    messagesContainer.appendChild(msgDiv);
    scrollToBottom();
}

function handleSuggestionClick(text) {
    const input = document.getElementById('chatbot-input');
    const form = document.getElementById('chatbot-form');
    if (input && form) {
        input.value = text;
        form.dispatchEvent(new Event('submit'));
    }
}

function showTypingIndicator() {
    const messagesContainer = document.getElementById('chatbot-messages');
    if (!messagesContainer) return;

    const typingDiv = document.createElement('div');
    typingDiv.id = 'chatbot-typing';
    typingDiv.className = 'chat-message message-bot typing-message';
    typingDiv.innerHTML = `
        <div class="bot-avatar">IF</div>
        <div class="message-body">
            <div class="typing-dots">
                <span></span><span></span><span></span>
            </div>
        </div>
    `;
    messagesContainer.appendChild(typingDiv);
    scrollToBottom();
}

function removeTypingIndicator() {
    const typingDiv = document.getElementById('chatbot-typing');
    typingDiv?.remove();
}

function scrollToBottom() {
    const messagesContainer = document.getElementById('chatbot-messages');
    if (messagesContainer) {
        messagesContainer.scrollTop = messagesContainer.scrollHeight;
    }
}

function formatTime() {
    const d = new Date();
    return d.toLocaleTimeString([], { hour: '2-digit', minute: '2-digit' });
}

function escapeHtml(str) {
    return str.replace(/&/g, "&amp;")
              .replace(/</g, "&lt;")
              .replace(/>/g, "&gt;")
              .replace(/"/g, "&quot;")
              .replace(/'/g, "&#039;");
}

function formatMarkdown(str) {
    if (!str) return '';
    let text = str;
    
    // Bold **text**
    text = text.replace(/\*\*(.*?)\*\*/g, '<strong>$1</strong>');
    // Italic *text*
    text = text.replace(/\*(.*?)\*/g, '<em>$1</em>');
    // Code `code`
    text = text.replace(/`(.*?)`/g, '<code class="chat-code">$1</code>');
    // Line breaks
    text = text.replace(/\n/g, '<br>');

    return text;
}
