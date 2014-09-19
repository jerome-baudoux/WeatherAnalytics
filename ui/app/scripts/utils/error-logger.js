'use strict';

/**
 * This is NOT an AngularJS object so that is can be used outside angularJS
 * @constructor
 */
function ErrorLogger() {
    ErrorLogger.LOCALSTORAGE_PREFIX_KEY = 'LOGS_TO_KEEP';
    ErrorLogger.LOCALSTORAGE_INDEX_KEY = 'LOGS_INDEX';
    this.nbChunks = 10;
    this.linesInChunks = 100;
}

/**
 * Get an instance of the ErrorLogger
 */
ErrorLogger.getInstance = function() {
    if(!ErrorLogger.inst) {
        ErrorLogger.inst = new ErrorLogger();
    }
    return ErrorLogger.inst;
};

/**
 * Specify the size of the chunks we want to keep
 */
ErrorLogger.getInstance.setNbChunks = function(nbChunks) {
    this.nbChunks = nbChunks;
};

/**
 * Specify the amount of lines in a chunk
 */
ErrorLogger.getInstance.setLinesInChunks = function(nbLines) {
    this.linesInChunks = nbLines;
};

/**
 * Do the actual business
 */
ErrorLogger.prototype.handleError = function (message, error, doNotShowInConsole) {
    try {
        var line = this.formatLine(message);
        if (error && error.stack) {
            line += '\n' + error.stack;
        }

        if (!doNotShowInConsole) {
            console.error(line);
        }

        this.addToCurrentChunk(line);
    } catch (e) {
        console.error(e.stack);
    }
};

/**
 * Fetch the logs
 */
ErrorLogger.prototype.getLogs = function() {
    try {
        var storage = this.getLocalStorage();
        if (storage) {
            var index = this.getIndex();
            var content = '';
            for (var i = index.firstChunk; i <= index.currentChunk; i++) {
                if (content) {
                    content = content + '\n' + storage.getItem(ErrorLogger.LOCALSTORAGE_PREFIX_KEY + i);
                } else {
                    content = storage.getItem(ErrorLogger.LOCALSTORAGE_PREFIX_KEY + i);
                }
            }
            return content;
        } else {
            return 'Your browser does not support Local storage';
        }
    } catch (e) {
        console.error(e.stack);
    }
};

/**
 * Clear all logs
 */
ErrorLogger.prototype.clearLogs = function() {

    try {
        var storage = this.getLocalStorage();
        if (storage) {
            storage.clear();
        }
    } catch (e) {
        console.error(e.stack);
    }
};

/*
 * Private
 */

/**
 * Boiler-plate for fetching local storage
 */
ErrorLogger.prototype.getLocalStorage = function() {
    if(typeof(Storage) !== 'undefined') {
        return window.localStorage;
    }
    return undefined;
};

/**
 * Format the message
 */
ErrorLogger.prototype.formatLine = function(line) {
    var now = new Date();
    var date =
        now.getFullYear() + '-' +
        ('00' + now.getDate()).slice(-2) + '-' +
        ('00' + (now.getMonth() + 1)).slice(-2) + ' ' +
        ('00' + now.getHours()).slice(-2) + ':' +
        ('00' + now.getMinutes()).slice(-2) + ':' +
        ('00' + now.getSeconds()).slice(-2);
    return date + ' | ' + line;
};

/**
 * Create an empty index
 */
ErrorLogger.prototype.createIndex = function() {
    return {
        firstChunk: 0,
        currentChunk: 0
    };
};

/**
 * Fetch the current index
 */
ErrorLogger.prototype.getIndex = function() {
    var storage = this.getLocalStorage();
    var index;
    if(storage) {
        try {
            var json = storage.getItem(ErrorLogger.LOCALSTORAGE_INDEX_KEY);
            if(json) {
                index = JSON.parse(json);
            }
        } catch (e) {
            console.log('Log index cannot be found or is corrupted');
        }
    }
    if(!index) {
        index = this.createIndex();
        this.saveIndex(index);
    }
    return index;
};

/**
 * Save a new index
 */
ErrorLogger.prototype.saveIndex = function(index) {

    var storage = this.getLocalStorage();
    if(storage) {
        try {
            storage.setItem(ErrorLogger.LOCALSTORAGE_INDEX_KEY, JSON.stringify(index));
        } catch (e) {
            console.log('Log index cannot be saved');
        }
    }
};

/**
 * Count the lines inside a chunk
 */
ErrorLogger.prototype.countLines = function(line) {
    if(!line) {
        return 0;
    }
    return line.split('\n').length;
};

/**
 * Do not keep old logs
 */
ErrorLogger.prototype.removeOldLogs = function(index) {
    var storage = this.getLocalStorage();
    if(storage) {
        try {
            if (index.currentChunk - index.firstChunk > this.nbChunks) {
                for (var i = index.firstChunk; i <= index.currentChunk - this.nbChunks; i++) {
                    storage.removeItem(ErrorLogger.LOCALSTORAGE_PREFIX_KEY + i);
                }
                index.firstChunk = index.currentChunk - this.nbChunks + 1;
            }
        } catch (e) {
            console.log('Could not clear logs');
        }
    }
};

/**
 * Add a line to the current chunk
 */
ErrorLogger.prototype.addToCurrentChunk = function(line) {
    var index = this.getIndex();
    var storage = this.getLocalStorage();
    if(storage) {
        var key = ErrorLogger.LOCALSTORAGE_PREFIX_KEY + index.currentChunk;
        var content = storage.getItem(key);
        var nbLines = this.countLines(content);
        if(nbLines>this.linesInChunks) {
            index.currentChunk++;
            this.removeOldLogs(index);
            this.saveIndex(index);
            content = '';
            key = ErrorLogger.LOCALSTORAGE_PREFIX_KEY + index.currentChunk;
        }
        if(content) {
            content = content + '\n' + line;
        } else {
            content = line;
        }
        storage.setItem(key, content);
    }
};
